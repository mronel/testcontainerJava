package com.tests.integration.integrationtests.cucumber;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;

@ActiveProfiles("test")
@CucumberContextConfiguration
@SpringBootTest()
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/feature", glue = "com.tests.integration.integrationtests.cucumber.stepdefinition")
public class CucumberTest {
}
