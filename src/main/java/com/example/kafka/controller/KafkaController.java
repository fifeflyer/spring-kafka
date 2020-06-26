package com.example.kafka.controller;

import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.kafka.model.KafkaMessage;
import com.example.kafka.publish.KafkaProducer;
import lombok.NonNull;

@RestController
@ResponseStatus(CREATED)
public class KafkaController {

    private final KafkaProducer producer;

    @Value(value = "${kafka.topic}")
    private String topic;

    @Autowired
    public KafkaController(KafkaProducer producer) {
        this.producer = producer;
    }

    @PostMapping(value = "/kafka/message")
    public void publishMessage(@RequestBody @NonNull KafkaMessage<?> message) {
        producer.sendMessage(topic, message);
    }

    @PostMapping(value = "/kafka/order")
    public void publishOrder(@RequestBody @NonNull KafkaMessage<?> message) {
        producer.sendMessage("orders", message);
    }
}
