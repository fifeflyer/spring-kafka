package com.example.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.kafka.model.KafkaMessage;
import com.example.kafka.publish.KafkaProducer;
import lombok.NonNull;

@RestController
public class KafkaController {

    private final KafkaProducer producer;

    @Autowired
    public KafkaController(KafkaProducer producer) {
        this.producer = producer;
    }

    @PostMapping(value = "/kafka/message")
    public void publishMessage(@RequestBody @NonNull KafkaMessage message) {
        producer.sendMessage(message.getText());
    }
}
