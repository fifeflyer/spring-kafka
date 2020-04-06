package com.example.kafka.consume;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "test")
    public void messageListener(String message) {
        log.info("Message: {}", message);
    }
}
