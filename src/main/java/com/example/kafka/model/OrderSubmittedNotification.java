package com.example.kafka.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(builderMethodName = "orderSubmittedNotification")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@EventType("ORDER_SUBMITTED")
public class OrderSubmittedNotification extends KafkaMessage<Timeline> {
}
