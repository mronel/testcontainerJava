package com.tests.integration.container.databases.configuration;


import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class DataSourceConfiguration {

  @Bean
  @ConfigurationProperties("spring.datasource.db1")
  public DataSourceProperties db1DataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @ConfigurationProperties("spring.datasource.db2")
  public DataSourceProperties db2DataSourceProperties() {
    return new DataSourceProperties();
  }

  @Primary
  @Bean(name = "db1DataSource")
  @ConfigurationProperties("spring.datasource.db1.hikari")
  public DataSource db1DataSource() {
    return db1DataSourceProperties()
            .initializeDataSourceBuilder()
            .build();
  }

  @Bean(name = "db2DataSource")
  @ConfigurationProperties("spring.datasource.db2.hikari")
  public DataSource db2DataSource() {
    return db2DataSourceProperties()
            .initializeDataSourceBuilder()
            .build();
  }
}