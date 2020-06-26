package com.example.kafka.consume;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.example.kafka.model.KafkaMessage;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderKafkaConsumer {

    @KafkaListener(id="kafka-orders", topics = "orders")
    public void messageListener(KafkaMessage<?> message) {
        log.info("Message: {}", message);
    }
}
