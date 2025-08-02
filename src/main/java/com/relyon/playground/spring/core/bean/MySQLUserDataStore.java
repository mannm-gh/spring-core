package com.relyon.playground.spring.core.bean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MySQLUserDataStore implements UserDataStore, InitializingBean, DisposableBean {

  public MySQLUserDataStore() {
    System.out.println(">> MySQLUserDataStore: MySQLUserDataStore instance created");
  }

  @Override
  public List<String> getUsers() {
    return List.of("User from MySQL");
  }

  @PostConstruct
  public void postConstruct() {
    System.out.println(">> @PostConstruct: MySQLUserDataStore is initializing");
  }

  @Override
  public void afterPropertiesSet() {
    System.out.println(">> MySQLUserDataStore: afterPropertiesSet() method called");
  }

  @PreDestroy
  public void preDestroy() {
    System.out.println(">> @PreDestroy: MySQLUserDataStore is cleaning up");
  }

  @Override
  public void destroy() {
    System.out.println(">> MySQLUserDataStore: destroy() method called");
  }
}
