# KATA - Development book
The goal of this kata is to find the best combination of discount on a list of book.

## Prerequisites
- Java 21 or higher
- Maven 3.6 or higher

## Getting started

### 1. Build the application

Use maven to build the application:
```bash
mvn clean install
```

### 2. Run the application
After building, run the application with:
```bash
mvn spring-boot:run
```

## Goal of the project
The goal of the project was to show the TDD development of the main functionality (calculate different combination of discount and give the best result).

You can check the commit to see the TDD development.

A simple postman file is in the project to test the api and get the result from a list of books **ID**.

The **BasketServiceTest** will test the main functionality through the service.