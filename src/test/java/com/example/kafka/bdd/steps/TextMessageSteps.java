package com.example.kafka.bdd.steps;

import static com.example.kafka.model.TextMessage.textMessage;
import static java.util.Objects.nonNull;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

import com.example.kafka.model.KafkaMessage;
import com.example.kafka.model.TextMessage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TextMessageSteps extends StepBase {

    private static final String EVENT_TYPE = "TEXT";
    private static final String TEXT_MESSAGE = "This is a test";
    private static final String MESSAGE_ENDPOINT = "http://localhost:10090/spring-kafka/kafka/message";

    @When("^a request to publish a text message is submitted$")
    public void aRequestToPublishATextMessageIsSubmitted() {
        restTemplate.postForEntity(MESSAGE_ENDPOINT, createTextMessage(), String.class);
    }

    @Then("^a Kafka text message should be published and consumed$")
    public void aKafkaTextMessageShouldBePublishedAndConsumed() {
        await().atMost(5, SECONDS).until(this::textConsumed);
    }

    private TextMessage createTextMessage() {
        return textMessage()
                .eventType(EVENT_TYPE)
                .data(TEXT_MESSAGE)
                .build();
    }

    private Boolean textConsumed() {
        KafkaMessage<?> message = testContext.getConsumedTextMessage();
        return nonNull(message) && message.getEventType().equals(EVENT_TYPE);
    }
}
