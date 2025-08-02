package com.relyon.playground.spring.core;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import static com.relyon.playground.spring.core.BeanInspector.listSpringProvidedBeans;
import static com.relyon.playground.spring.core.BeanInspector.listUserDefinedBeans;

public class Application {

  public static void main(String[] args) {
    try (AbstractApplicationContext context = new AnnotationConfigApplicationContext("com.relyon.playground.spring.core")) {
      listUserDefinedBeans(context);
      listSpringProvidedBeans(context);
    }
  }
}
