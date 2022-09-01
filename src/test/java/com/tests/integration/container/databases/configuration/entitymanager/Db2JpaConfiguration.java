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
        basePackages = "com.tests.integration.app1.repository",
        entityManagerFactoryRef = "db2EntityManagerFactory",
        transactionManagerRef = "db2TransactionManager"
)
public class Db2JpaConfiguration {

  @Bean
  public LocalContainerEntityManagerFactoryBean db2EntityManagerFactory(
          @Qualifier("db2DataSource") DataSource dataSource, EntityManagerFactoryBuilder builder) {
    return builder
            .dataSource(dataSource)
            .packages("com.tests.integration.app1.domain")
            .build();
  }

  @Bean
  public PlatformTransactionManager db2TransactionManager(
          @Qualifier("db2EntityManagerFactory") LocalContainerEntityManagerFactoryBean db2EntityManagerFactory) {
    return new JpaTransactionManager(Objects.requireNonNull(db2EntityManagerFactory.getObject()));
  }

}
