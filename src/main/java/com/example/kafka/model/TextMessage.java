package com.example.kafka.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@EventType("TEXT")
public class TextMessage extends KafkaMessage<String> {
}
