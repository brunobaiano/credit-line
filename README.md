# Credit Line

Based on the Hexagonal Architecture.

I understand that other tool should provide the non-functional requirements.
It was implemented a bucket to demonstrate that this could be done.

## How to run

**Need java 17**

Run the jar:

```
java -jar credit-line-0.0.1-SNAPSHOT.jar
```

Build application using maven:

```
mvn package
run the jar on target folder
```

You can use swagger to test:

`http://localhost:8080/swagger-ui/index.html`

## Application - Adapters
User Side. Implemented Rest Controller.

## Domain - Service
I have an interface - port called `CreditLineService` and my `TribalCreditService` implementation 
representing my domain and Business rules.

## Infrastructure

In this example, I didn't use any database or the JPA/Repository.
Only `BeanConfiguration` with SpringBoot annotations.

### Tests

`TribalCreditServiceTest` uses a ParametrizedTest to test different scenarios to call the service.
`CreditLineControllerTest` Unit test to see rate api.

A jacoco report can be generated using mvn command before and can be found in:
```
target/site/jacoco/index.html
```

### Monitoring

Basic monitoring using actuator.


### Improvements

* Implement the limit when application is rejected
* Implement cache for a real scenario
* Test Spring Cloud Gateway to replace Bucket4J
* Remove Bucket4J and use an external tool to limit
* Using micrometer to export metrics to Prometheus or other tool
* Using logstash-logging-spring-boot-starter to export logs to ELK

### Minor improvements

* Enum for foundingType
* Factory for CreditLineRequest ?
* Extract inline messages to variables. 



