package com.example.kafka.bdd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import com.example.kafka.bdd.steps.EventSteps;

@Configuration
public class CucumberConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public EventSteps.TestConsumer testConsumer() {
        return new EventSteps.TestConsumer();
    }
}

