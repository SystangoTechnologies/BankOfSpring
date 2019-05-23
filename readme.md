![BankOfSpring](https://github.com/SystangoTechnologies/BankOfSpring/blob/master/src/main/resources/images/logo.png)

## BankOfSpring
Production ready maven based Spring Boot starter kit application with example cases of handling transactions with Spring.

## Description
Starter kit for booting up the development of a API oriented and transaction based spring Java server. It contains the best practices and latest tools that a spring boot developer should opt for in a fresh development. Since JPA is used, developers are free to opt for any SQL based DB engine for persistence (H2 has been used as an example with this project). The preferred IDE for development is IntelliJ which comes with a plethora of useful JAVA tools to support Spring Boot development, but developers are free to opt for Eclipse or STS as well. The focus in this project is solely upon the SpringBoot development with business cases involving transactions and writting proper unit and integration tests for them.

## Technology

- **Spring Boot**     - Server side framework
- **JPA**             - Entity framework
- **Lombok**          - Provides automated getter/setters
- **Actuator**        - Application insights on the fly
- **Spring Security** - Spring's security layer
- **Thymeleaf**       - Template Engine
- **Devtools**        - Support Hot-Code Swapping with live browser reload
- **JJWT**            - JWT tokens for API authentication
- **Swagger**         - In-built swagger2 documentation support
- **Docker**          - Docker containers
- **Junit**           - Unit testing framework
- **H2**              - H2 database embedded version

## Application Structure

## Running the server locally
The BankOfSpring application can be started using your favourite IDE and its run configuration support. If you are a terminal savvy, please use the following command -

````
mvn spring-boot:run
````

## Docker
BankOfSpring supports docker container out of the box. This boilerplate is meant to cater to both web based applications as well as scalable micro services written in Java. Please select one of the following two ways to use docker to build and run the application -

**Dockerfile**

To build a fresh image, use -
````
docker build -t bankofspring .
````
To run the new image, use -
````
docker run -p 8080:8080 bankofspring
````

**Docker-Compose**

To build a fresh image, use -
````
docker-compose build
````
To run the new image, use -
````
docker-compose up
````

## Swagger Documentation
Swagger documentation is in-built in this starter-kit and can be accessed at the following URL -
````
http://<host-name>:8080/swagger-ui.html
````

## Unit test cases
There are multiple unit test cases written to cover the different components of the application. However there is a global application test suite file _**BankOfSpringApplicationUnitTests.java**_ that combines all the test cases in a logical manner to create a complete suite. It can be run from command prompt using the following command -

````
mvn clean test -Dtest=BankOfSpringApplicationUnitTests
````

## Integration test cases
There are multiple integration test cases written to cover the different components of the application. However there is a global application test suite file _**BankOfSpringApplicationTests.java**_ that combines all the test cases in a logical manner to create a complete suite. It can be run from command prompt using the following command -

````
mvn clean test -Dtest=BankOfSpringApplicationTests
````

## Contributors
[Arpit Khandelwal](https://www.linkedin.com/in/arpitkhandelwal1984/)

## License
This project is licensed under the terms of the MIT license.
