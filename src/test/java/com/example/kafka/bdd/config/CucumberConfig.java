package com.example.kafka.bdd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.client.RestTemplate;
import com.example.kafka.model.CucumberContext;
import com.example.kafka.model.KafkaMessage;

@Configuration
@PropertySource(value = "classpath:application-test.properties")
public class CucumberConfig {

    @Autowired
    private CucumberContext context;

    @Bean
    public CucumberContext cucumberContext() {
        return new CucumberContext();

    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public OrderConsumer orderConsumer() {
        return new OrderConsumer();
    }

    @Bean
    public TextMessageConsumer textMessageConsumer() {
        return new TextMessageConsumer();
    }

    private class OrderConsumer {
        @KafkaListener(id = "kafka-test-orders", topics = "orders")
        public void messageListener(KafkaMessage<?> message) {
            context.setConsumedOrderNotification(message);
        }
    }

    private  class TextMessageConsumer {
        @KafkaListener(id = "kafka-test-events", topics = "texts")
        public void messageListener(KafkaMessage<?> message) {
            context.setConsumedTextMessage(message);
        }
    }
}

