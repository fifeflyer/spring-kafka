package com.example.kafka.consume;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.example.kafka.model.KafkaMessage;
import com.example.kafka.model.TextMessage;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(id="kafka-events", topics = {"test", "texts"})
    public void messageListener(KafkaMessage<TextMessage> message) {
        log.info("Message: {}", message);
    }
}
