package com.example.kafka.helper;

import static com.google.common.collect.Collections2.filter;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.internal.verification.VerificationModeFactory.atLeast;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;

/**
 * Add a JUnit rule for Logback verifier to the unit test class:
 *
 * <pre>{@code
 * @Rule
 * public LogbackVerifier logbackVerifier = new LogbackVerifier();
 * }</pre>
 * <p>
 * Then set up the logging expectations in the unit tests prior to executing the code under test:
 *
 * <pre>{@code
 * @Test
 * public void shouldLogAllOtherUsageManagementExceptionsAsErrorMessages() throws Exception {
 *     logbackVerifier.expectMessage(Level.ERROR, "Message Text");
 *     serviceUnderTest.doSomething();
 * }
 * }</pre>
 */
public class LogbackVerifier implements TestRule {

    private final List<ExpectedLogEvent> expectedLogEvents = new ArrayList<>();

    private final Predicate<ExpectedLogEvent> unmatchedLoggingEvents =
            expectedLoggingEvent -> expectedLoggingEvent.matched.equals(false);

    @Captor
    private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

    @Mock
    private Appender<ILoggingEvent> mockedAppender;

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                before();
                try {
                    base.evaluate();
                    verifyLogging();
                } finally {
                    after();
                }
            }
        };
    }

    public void expectMessage(Level level, String msg) {
        expectedLogEvents.add(new ExpectedLogEvent(level, msg));
    }

    private void before() {
        initMocks(this);

        when(mockedAppender.getName()).thenReturn("MockedAppender");
        ((Logger) getLogger("com.bskyb")).addAppender(mockedAppender);
    }

    private void after() {
        ((Logger) getLogger("com.bskyb")).detachAppender(mockedAppender);
    }

    private void verifyLogging() {
        if (!expectedLogEvents.isEmpty()) {
            verify(mockedAppender, atLeast(1)).doAppend(captorLoggingEvent.capture());

            for (LoggingEvent loggingEvent : captorLoggingEvent.getAllValues()) {
                for (ExpectedLogEvent expectedLoggingEvent : expectedLogEvents) {
                    if (expectedLoggingEvent.matches(loggingEvent)) {
                        expectedLoggingEvent.matched = true;
                    }
                }
            }

            assertThat(filter(expectedLogEvents, unmatchedLoggingEvents::test), hasSize(0));
        }
    }

    private static final class ExpectedLogEvent {
        private final String message;
        private final Level level;
        private Boolean matched;

        ExpectedLogEvent(Level level, String message) {
            this.message = message;
            this.level = level;
            this.matched = false;
        }

        boolean matches(ILoggingEvent actual) {
            return actual.getFormattedMessage().startsWith(message)
                    && actual.getLevel().equals(level);
        }

        @Override
        public String toString() {
            return "[" + level + "] " + message;
        }
    }
}
