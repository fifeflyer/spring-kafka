package com.example.kafka.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@EventType("ORDER_SUBMITTED")
public class OrderSubmittedNotification extends KafkaMessage<Timeline> {

    public OrderSubmittedNotification() {
        setEventType("ORDER_SUBMITTED");
    }
}
