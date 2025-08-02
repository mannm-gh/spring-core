# Spring Bean

- Definition: A Spring bean is a Java object managed by the Spring IoC Container.
- Lifecycle: https://medium.com/@TheTechDude/spring-bean-lifecycle-full-guide-f865966e89ce
- Bean Scopes: Singleton (default), Prototype, Request, Session, Application, Websocket, Custom scopes
- Bean Declaration: `@Controller`/`@RestController`, `@Service`, `@Repository`, `@Component`, `@Bean`, `@Primary`
- Bean Injection: `@Autowired`, `@Qualifier`
- Bean Creation/Destruction custom logic: `@PostConstruct`, `@PreDestroy`
- Bean Lazy Initialization: `@Lazy` (per-bean), `spring.main.lazy-initialization=true` (all beans)

---

# Problems and Solutions

This part highlights common issues encountered when working with Spring Beans and provides practical solutions and
best practices for solving them effectively.

---

## 🚫 Problem 1 - Ambiguity When Retrieving/Injecting Beans of the Same Type

When multiple beans of the same type are defined in the Spring context, attempting to retrieve one by type alone will
result in an error.

---

### ❌ Error when Retrieving Bean from the Spring IOC Container

```java
@Configuration
public class BeanConfiguration {

  @Bean
  public UserDataStore mySQLUserDataStore() {
    return new MySQLUserDataStore();
  }

  @Bean
  public UserDataStore postgreSQLUserDataStore() {
    return new PostgreSQLUserDataStore();
  }
}

/*
 * ❌ This code will fail with a NoUniqueBeanDefinitionException at runtime.
 *
 * Reason:
 * - There are multiple beans of type UserDataStore defined in the Spring context:
 *     1. mySQLUserDataStore
 *     2. postgreSQLUserDataStore
 * - When calling context.getBean(UserDataStore.class), Spring cannot determine which bean to return.
 */
UserDataStore userDataStore = context.getBean(UserDataStore.class);
```

---

### ❌ Error when Injecting Bean

```java
/*
 * ❌ This code will fail with a NoUniqueBeanDefinitionException at compile-time.
 *
 * Reason:
 * - There are multiple beans of type UserDataStore defined in the Spring context:
 *     1. mySQLUserDataStore
 *     2. postgreSQLUserDataStore
 * - When injecting UserDataStore to UserService, Spring cannot determine which bean to inject.
 */
@Component
public class MySQLUserDataStore implements UserDataStore {}

@Component
public class PostgreSQLUserDataStore implements UserDataStore {}

@Service
public class UserService {

  private final UserDataStore userDataStore;

  public UserService(UserDataStore userDataStore) {
    this.userDataStore = userDataStore;
  }
}
```

---

### ✅ Solution 1: Use `@Primary`

```java
@Bean
@Primary
public UserDataStore mySQLUserDataStore() {
  return new MySQLUserDataStore();
}
```

---

### ✅ Solution 2.1 (Retrieving): Use Bean Name and Bean Type

```java
UserDataStore userDataStore = context.getBean("mySQLUserDataStore", UserDataStore.class);
```

Avoid using only the bean name, as Spring will return the bean as an Object type. This approach lacks type safety and
may lead to runtime casting errors, as we may need to manually cast the object.

---

### ✅ Solution 2.2 (Injecting): Use `@Qualifier`

When using `@Autowired`, you can resolve ambiguity like this:

```java
public UserService(@Qualifier("postgreSQLUserDataStore") UserDataStore userDataStore) {
  this.userDataStore = userDataStore;
}
```

---

## 🚫 Problem 2 - Return Type in `@Bean` Methods: Interface vs. Concrete Type

### ✅ Returning the Interface Type (Recommended)

```java
@Bean
public UserDataStore mySQLUserDataStore() {
  return new MySQLUserDataStore();
}
```

**Pros:**

- Promotes loose coupling and abstraction.
- Easier to swap implementations later.
- Better for testing and mocking.

**Cons:**

- You lose access to subclass-specific methods unless you cast.

---

### 🟡 Returning the Concrete Type

```java
@Bean
public MySQLUserDataStore mySQLUserDataStore() {
  return new MySQLUserDataStore();
}
```

**Pros:**

- Direct access to implementation-specific methods.
- Useful in infrastructure or tightly bound configurations.

**Cons:**

- Tighter coupling to implementation.
- Less flexible for future changes.

---

### 📦 Summary

| Approach                           | Abstraction                                     | Flexibility | Risk of Conflict                |
| ---------------------------------- | ----------------------------------------------- | ----------- | ------------------------------- |
| Return interface (`UserDataStore`) | ✅ High                                         | ✅ High     | ⚠️ Yes, if multiple beans exist |
| Return concrete class              | ❌ Low                                          | ❌ Low      | ✅ Unique if one per class      |

---

## 📘 Recommendations

- Always prefer **interface return types** for `@Bean` methods unless subclass features are absolutely required.
- Use **`@Qualifier`** or **`@Primary`** when multiple implementations exist.
- Avoid relying on concrete class retrieval unless necessary.
