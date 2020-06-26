Feature: Test Kafka Events

  Scenario: Order Submitted Notifications
    When a request to publish an order submitted notification is submitted
    Then a Kafka order submitted event should be published and consumed
