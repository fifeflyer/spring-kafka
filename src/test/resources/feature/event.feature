@kafka
Feature: Test Kafka Events

  Scenario: Order Submitted Notifications
    When a request to publish an order submission notification is submitted
    Then a Kafka order submission event should be published and consumed

  Scenario: Text Messages
    When a request to publish a text message is submitted
    Then a Kafka text message should be published and consumed