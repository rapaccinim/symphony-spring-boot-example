# Converting existing Symphony project to Spring Boot
## About
Simple project that demonstrates how to convert an existing Symphony project to Spring Boot.

## How to
* dependencies: have a look at the `pom.xml` file to see which dependencies are necessary
* configuration
  * move the content of the `config.yaml` inside a new file called `application.yaml` (the one used by Spring Boot)
  * nest the config under `bdk:`
  * delete the old no more useful `config.yaml`
  * inside `application.yaml` add the logging side:
```
logging:
  level:
    com.symphony: debug
```
* main class:
  * add the `@SpringBootApplication`
  * use the default run inside the `main` method: `SpringApplication.run(BotApplication.class, args);`
* TBD: how to do real time event handling in Spring Boot
* TBD: how to do Slash Commands in Spring Boot
* Activities in Spring Boot:
  * they are easy to do, just add the `@Component` annotation for the `class`