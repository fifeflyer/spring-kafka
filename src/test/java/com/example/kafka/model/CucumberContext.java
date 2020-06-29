package com.example.kafka.model;

import lombok.Data;

@Data
public class CucumberContext {

    private KafkaMessage<?> consumedOrderNotification;
    private KafkaMessage<?> consumedTextMessage;
}
