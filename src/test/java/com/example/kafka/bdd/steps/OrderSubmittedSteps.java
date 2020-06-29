package com.example.kafka.bdd.steps;

import static com.example.kafka.model.OrderSubmittedNotification.orderSubmittedNotification;
import static com.example.kafka.model.Timeline.timeline;
import static java.util.Objects.nonNull;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

import org.springframework.test.context.ContextConfiguration;
import com.example.kafka.bdd.config.CucumberConfig;
import com.example.kafka.config.KafkaConsumerConfig;
import com.example.kafka.model.KafkaMessage;
import com.example.kafka.model.OrderSubmittedNotification;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@ContextConfiguration(classes = {CucumberConfig.class, KafkaConsumerConfig.class})
public class OrderSubmittedSteps extends StepBase {

    private static final String EVENT_TYPE = "ORDER_SUBMITTED";
    private static final String TIMELINE_ID = "timeline";
    private static final String ORDER_ENDPOINT = "http://localhost:10090/spring-kafka/kafka/order";

    @When("^a request to publish an order submission notification is submitted$")
    public void aRequestToPublishAnOrderSubmissionNotificationIsSubmitted() {
        restTemplate.postForEntity(ORDER_ENDPOINT, createOrderSubmittedNotification(), String.class);
    }

    @Then("^a Kafka order submission event should be published and consumed$")
    public void aKafkaOrderSubmissionEventShouldBePublishedAndConsumed() {
        await().atMost(5, SECONDS).until(this::orderConsumed);
    }

    private OrderSubmittedNotification createOrderSubmittedNotification() {
        return orderSubmittedNotification()
                .eventType(EVENT_TYPE)
                .data(timeline().id(TIMELINE_ID).build())
                .build();
    }

    private Boolean orderConsumed() {
        KafkaMessage<?> notification = testContext.getConsumedOrderNotification();
        return nonNull(notification) && notification.getEventType().equals(EVENT_TYPE);
    }
}
