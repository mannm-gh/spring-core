```java
@GetMapping("/generate-image")
  public RedirectView generate() {
    return new RedirectView(openAiImageModel
      .call(new ImagePromt("Give me some image"))
      .getResult()
      .getOutput()
      .getUrl());
  }
```

The goal is to compile your Spring Boot app into a native binary using GraalVM for:

- Faster startup
- Lower memory usage
- Deployment without a JVM

```shell
./mvnw clean package -Pnative native:compile
```

```java
@Mapper
  public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUseDto(User user);
  }
```
