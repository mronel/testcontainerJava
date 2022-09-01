package com.tests.integration.container.databases.configuration.properties;


import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@Profile("test")
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceProperties implements BeanClassLoaderAware, InitializingBean {

  private String driverClassName;

  private String url;

  private String username;

  private String password;


  @Override
  public void setBeanClassLoader(ClassLoader classLoader) {

  }

  @Override
  public void afterPropertiesSet() throws Exception {

  }
}
