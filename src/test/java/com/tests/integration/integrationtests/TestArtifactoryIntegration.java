package com.tests.integration.integrationtests;


import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@DirtiesContext
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest()
public class TestArtifactoryIntegration {

  public static final GenericContainer<?> container = new GenericContainer<>(
          DockerImageName.parse(
                  "psp-docker-dev-local/receivable-financial-manager-monitors:1.0.172"));


  @BeforeEach
  public void startContainer() {
    container.start();
  }

  @Test
  public void testIntegration() {

    var verify = container.isCreated();
    Assert.assertTrue(verify);

  }

}
