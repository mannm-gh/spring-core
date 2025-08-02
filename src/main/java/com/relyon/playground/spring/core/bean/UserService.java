package com.relyon.playground.spring.core.bean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements InitializingBean, DisposableBean {

  private final UserDataStore userDataStore;

  public UserService(UserDataStore userDataStore) {
    System.out.println(">> UserService: UserService instance created");
    this.userDataStore = userDataStore;
  }

  public List<String> getUsers() {
    return userDataStore.getUsers();
  }

  @PostConstruct
  public void postConstruct() {
    System.out.println(">> @PostConstruct: UserService method is initializing");
  }

  @Override
  public void afterPropertiesSet() {
    System.out.println(">> UserService: afterPropertiesSet() called");
  }

  @PreDestroy
  public void preDestroy() {
    System.out.println(">> @PreDestroy: UserService is cleaning up");
  }

  @Override
  public void destroy() {
    System.out.println(">> UserService: destroy() method called");
  }
}
