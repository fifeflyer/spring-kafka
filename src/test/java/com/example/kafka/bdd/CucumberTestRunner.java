package com.example.kafka.bdd;

import static io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:feature",
        glue = {"com.example.kafka.bdd.steps"},
        plugin = {
                "pretty",
                "timeline:target/site/cucumber/timeline-report",
                "html:target/site/cucumber/basic-report",
                "json:target/site/cucumber/json-report.json"
        },
        snippets = CAMELCASE,
        strict = true,
        tags = {"not @wip"})
public class CucumberTestRunner {
}
