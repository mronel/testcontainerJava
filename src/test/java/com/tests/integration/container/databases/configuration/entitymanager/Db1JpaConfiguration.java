package com.tests.integration.container.databases.configuration.entitymanager;

import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Profile("test")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.tests.integration.repository",
        entityManagerFactoryRef = "db1EntityManagerFactory",
        transactionManagerRef = "db1TransactionManager"
)
public class Db1JpaConfiguration {

  @Bean
  public LocalContainerEntityManagerFactoryBean db1EntityManagerFactory(
          @Qualifier("db1DataSource") DataSource dataSource, EntityManagerFactoryBuilder builder) {
    return builder
            .dataSource(dataSource)
            .packages("com.tests.integration.domain")
            .build();
  }

  @Bean
  public PlatformTransactionManager db1TransactionManager(
          @Qualifier("db1EntityManagerFactory") LocalContainerEntityManagerFactoryBean db1EntityManagerFactory) {
    return new JpaTransactionManager(Objects.requireNonNull(db1EntityManagerFactory.getObject()));
  }

}
