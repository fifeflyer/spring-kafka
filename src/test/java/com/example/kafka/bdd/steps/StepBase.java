package com.example.kafka.bdd.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import com.example.kafka.model.CucumberContext;

public abstract class StepBase {

    @Autowired
    CucumberContext testContext;

    @Autowired
    RestTemplate restTemplate;
}
