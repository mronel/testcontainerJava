package com.tests.integration.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerTopic {

  public static final String KAFKA_TEST_CONSUMER_FACTORY = "kafkaListenerContainerFactory";

  private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerTopic.class);


  @KafkaListener(topics = "topicexample", groupId = "kafkaexample", clientIdPrefix = "kafkaexample", containerFactory = KAFKA_TEST_CONSUMER_FACTORY)
  public void consume(String consumerRecord) {
    LOGGER.info("Consumindo a mensagem: {}", consumerRecord);
    validateMessage(consumerRecord);
  }

  public String validateMessage(String consumerRecord) {
    return consumerRecord;
  }

}
