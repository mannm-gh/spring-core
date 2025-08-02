package com.relyon.playground.spring.core.bean;

import java.util.List;

public class PostgreSQLUserDataStore implements UserDataStore {

  @Override
  public List<String> getUsers() {
    return List.of("User from PostgreSQL");
  }
}
