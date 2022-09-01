package com.tests.integration.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProducerTopic {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProducerTopic.class);

  @Autowired
  public KafkaTemplate<String, String> kafkaTemplate;

  public void send(ProducerRecord<String,String> record) {
    LOGGER.info("enviando mensagem='{}' para o tópico='{}' e com a key={}", record.value(), record.topic(), record.key());
    kafkaTemplate.send(record);
  }

}
