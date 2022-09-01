package com.tests.integration.container.kafka.configuration;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.shaded.com.google.common.collect.ImmutableMap;
import org.testcontainers.utility.DockerImageName;

import com.tests.integration.configuration.KafkaConsumerProducerConfiguration;

@ActiveProfiles("test")
@Component
public class KafkaContainerConfiguration {

  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaContainerConfiguration.class);

  public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

  public void startKafkaContainer(@Nullable String dockerImage, @Nullable String groupId) throws Exception {

    try {

      if (dockerImage != null) {
        kafka.setDockerImageName(dockerImage);
        LOGGER.info("> Setting docker image {} to start Kafka container...", dockerImage);
      } else {
        LOGGER.info("> Setting the latest version of Kafka docker image... ");
      }

      kafka.start();
      LOGGER.info("> Initializing Kafka Container");

      if (kafka.isCreated()) {
        LOGGER.info("> Initialized Kafka Container");

        KafkaConsumerProducerConfiguration.bootstrapServer = kafka.getBootstrapServers();
        LOGGER.info("> Setting Kafka bootstrap server...");

        if (groupId != null) {
          KafkaConsumerProducerConfiguration.groupId = groupId;
          LOGGER.info("> Setting groupId: {}", groupId);
        } else {
          KafkaConsumerProducerConfiguration.groupId = "kafkaexampletest";
          LOGGER.info("> Setting default groupId: kafkaexampletest");
        }
      }

    } catch (Exception ex) {
      throw new Exception(ex);
    }

  }

  public void createTopics(Collection<NewTopic> topicsAndPartitions) throws Exception {

    try {

      AdminClient adminClient = AdminClient.create(
              ImmutableMap.of(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConsumerProducerConfiguration.bootstrapServer)
      );

      adminClient.createTopics(topicsAndPartitions).all().get(30, TimeUnit.SECONDS);
      LOGGER.info("> Topicos criados com sucesso");

    } catch (Exception e) {
      throw new Exception(e);
    }

  }

  public String getBootstrapServer() {
    return kafka.getBootstrapServers();
  }

  public KafkaContainer getKafkaContainerInstance() {
    return kafka.isCreated() ? kafka : null;
  }

  public void getTopics() {

  }

  public void closeKafkaContainer() {
    kafka.close();
    LOGGER.info("> Turn off Kafka Container...");
  }

}
