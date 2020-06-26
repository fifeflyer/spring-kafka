package com.example.kafka.bdd.steps;

import static java.util.Objects.nonNull;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;
import com.example.kafka.bdd.config.CucumberConfig;
import com.example.kafka.config.KafkaConsumerConfig;
import com.example.kafka.model.KafkaMessage;
import com.example.kafka.model.OrderSubmittedNotification;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@ContextConfiguration(classes = {CucumberConfig.class, KafkaConsumerConfig.class})
public class EventSteps {

    private static KafkaMessage<?> consumedOrder;

    @Autowired
    private RestTemplate restTemplate;

    @When("^a request to publish an order submitted notification is submitted$")
    public void aRequestToPublishAnOrderSubmittedNotificationIsSubmitted() {
        restTemplate.postForEntity("http://localhost:10090/spring-kafka/kafka/message", new OrderSubmittedNotification(), String.class);
    }

    @Then("^a Kafka order submitted event should be published and consumed$")
    public void aKafkaOrderSubmittedEventShouldBePublishedAndConsumed() {
        await().atMost(5, SECONDS).until(this::orderConsumed);
    }

    public static class TestConsumer {
        @KafkaListener(topics = "orders")
        public void messageListener(KafkaMessage<?> message) {
            consumedOrder = message;
        }
    }

    private Boolean orderConsumed() {
        return nonNull(consumedOrder) && consumedOrder.getEventType().equals("ORDER_SUBMITTED");
    }
}
