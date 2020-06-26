package com.example.kafka.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.example.kafka.exception.EventMappingException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
class ControllerExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(EventMappingException.class)
    public void handleBadMappingRequest(EventMappingException e) {
        log.warn(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void defaultHandler(Exception e) {
        log.warn(e.getMessage());
    }
}
