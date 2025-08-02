package com.relyon.playground.spring.core;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Predicate;

public class BeanInspector {

  public static void listUserDefinedBeans(AbstractApplicationContext context) {
    listBeans(
      context, beanDefinition -> beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION, "User-defined bean:", RED
    );
  }

  public static void listSpringProvidedBeans(AbstractApplicationContext context) {
    listBeans(
      context, beanDefinition -> {
        int role = beanDefinition.getRole();
        return role == BeanDefinition.ROLE_INFRASTRUCTURE || role == BeanDefinition.ROLE_SUPPORT;
      }, "Spring-provided bean:", BLUE
    );
  }

  private static final String RESET = "\u001B[0m";
  private static final String RED = "\u001B[31m";
  private static final String CYAN = "\u001B[36m";
  private static final String YELLOW = "\u001B[33m";
  private static final String BLUE = "\u001B[34m";
  private static final String MAGENTA = "\u001B[35m";

  private static void listBeans(
    AbstractApplicationContext context,
    Predicate<BeanDefinition> filter,
    String label,
    String labelColor
  ) {
    ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();

    Arrays
      .stream(beanFactory.getBeanDefinitionNames())
      .filter(beanName -> filter.test(beanFactory.getBeanDefinition(beanName)))
      .map(beanName -> Map.entry(beanName, context.getBean(beanName)))
      .sorted(Comparator.comparing(entry -> entry.getValue().getClass().getName()))
      .forEach(entry -> {
        Object bean = entry.getValue();
        Class<?> beanClass = ClassUtils.getUserClass(bean);
        String className = beanClass.getSimpleName();
        String packageName = beanClass.getPackageName();
        boolean isProxy = ClassUtils.isCglibProxy(bean); // 👈 Detect if proxy

        System.out.printf(
          labelColor + "%s" + RESET + " " + "| " + CYAN + "%s" + RESET + " " + "| " + YELLOW + "Type:" + RESET + " " + CYAN + "%s (%s)" + RESET + " " + "| " + MAGENTA + "Proxy:" + RESET + " " + CYAN + "%s" + RESET + "%n", label, entry
            .getKey(), className, packageName, isProxy
        );
      });
  }
}
