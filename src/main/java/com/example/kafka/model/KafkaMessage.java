package com.example.kafka.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "eventType", visible = true, include = JsonTypeInfo.As.EXISTING_PROPERTY)
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = TextMessage.class, name = "TEXT"),
//        @JsonSubTypes.Type(value = OrderNotification.class, name = "ORDER_NOTIFICATION")
//})
@JsonTypeIdResolver(EventTypeResolver.class) // This removes the need to have the above annotations.
public class KafkaMessage<T> {

    private String eventType;
    private T data;

    public KafkaMessage() {
        setEventType(getClass().getAnnotation(EventType.class).value());
    }
}

