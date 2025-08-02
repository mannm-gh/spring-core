package com.relyon.playground.spring.core.bean;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class LoggingBeanPostProcessor implements BeanPostProcessor {

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) {
    System.out.println(">> BeanPostProcessor: Before Init - " + beanName);
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) {
    System.out.println(">> BeanPostProcessor: After Init - " + beanName);
    return bean;
  }
}
