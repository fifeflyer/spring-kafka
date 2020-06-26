package com.example.kafka.publish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import com.example.kafka.model.KafkaMessage;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<Object, Object> kafkaMessageTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<Object, Object> kafkaMessageTemplate) {
        this.kafkaMessageTemplate = kafkaMessageTemplate;
    }

    public void sendMessage(String topic, KafkaMessage<?> message) {
        ListenableFuture<SendResult<Object, Object>> future = kafkaMessageTemplate.send(topic, message);

        future.addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {

            @Override
            public void onSuccess(SendResult<Object, Object> result) {
                log.info("Sent message [{}] with offset [{}]", message, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable e) {
                log.info("Unable to send message [{}], reason [{}]", message, e.getMessage());
            }
        });
    }
}
