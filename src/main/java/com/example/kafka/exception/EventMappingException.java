package com.example.kafka.exception;

public class EventMappingException extends RuntimeException {

    public EventMappingException(String eventType) {
        super("Cannot map event type '" + eventType + "' - event skipped!");
    }
}
