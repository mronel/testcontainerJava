package com.tests.integration.integrationtests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import com.tests.integration.configuration.KafkaConsumerProducerConfiguration;
import com.tests.integration.consumer.ConsumerTopic;
import com.tests.integration.container.kafka.configuration.KafkaContainerConfiguration;
import com.tests.integration.producer.ProducerTopic;

@DirtiesContext
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest()
public class TestKafka {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerTopic.class);
  private static final String TOPIC = "topicexample";
  private static final Integer PARTITIONS = 10;
  private static final String GROUP_ID = "kafkagroup";

  @Autowired
  private KafkaContainerConfiguration kafkaContainerConfiguration;

  @Autowired
  public ProducerTopic producer;

  @Autowired
  public ConsumerTopic consumer;

  @BeforeEach
  public void configKafkaContainer() throws Exception {

    kafkaContainerConfiguration.startKafkaContainer(null, GROUP_ID);

    Collection<NewTopic> topicsAndPartitions = new ArrayList<>();
    topicsAndPartitions.add(new NewTopic(TOPIC, PARTITIONS, (short) 1));
    topicsAndPartitions.add(new NewTopic("exampletest2", PARTITIONS, (short) 1));

    kafkaContainerConfiguration.createTopics(topicsAndPartitions);

  }

  @Test
  public void verifyContainerKafka() {
    Assert.assertTrue(kafkaContainerConfiguration.getKafkaContainerInstance().isRunning());
  }


  @Test
  public void consumingAndProducingMessages() {

    producer.kafkaTemplate.setDefaultTopic(TOPIC);

    producer.send(new ProducerRecord<>(TOPIC, "1", "primeira mensagem"));
    producer.send(new ProducerRecord<>(TOPIC, "2", "segunda mensagem"));
    producer.send(new ProducerRecord<>(TOPIC, "3", "terceira mensagem"));
    producer.send(new ProducerRecord<>(TOPIC, "4", "quarta mensagem"));


    Consumer<String, String> newConsumer = new KafkaConsumerProducerConfiguration().consumerFactory().createConsumer();
    newConsumer.subscribe(Collections.singletonList(TOPIC));

    List<String> validateMessages = new ArrayList<>();

    KafkaTestUtils.getRecords(newConsumer).forEach(record -> {
      validateMessages.add(consumer.validateMessage(record.value().concat(" teste")));
    });

    Assert.assertTrue(validateMessages.stream().findFirst().stream().anyMatch(msg -> msg.contains("quarta")));
  }
}
