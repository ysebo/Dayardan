# Interview Preparation System

## Overview
The Interview Preparation System is a Spring Boot-based backend application designed to facilitate interview sessions with multiple-choice questions. It allows users to start interview sessions, retrieve session questions, and submit answers while tracking correctness.
## Table of Contents
1. [Features](#features)
2. [Technologies Used](#technologies-used)
3. [Setup and Installation](#setup-and-installation)
4. [Database Configuration](#database-configuration)
5. [API Documentation](#api-documentation)
6. [Testing](#testing)
7. [Endpoints](#endpoints)
8. [Exception Handling](#exception-handling)
9. [Data Validation](#data-validation)
    
## Features
- **Dynamic interview session creation**
- **Randomized question selection**
- **Answer submission and validation**
- **Role-based access control**
- **Data Validation**: Validate input data using **Hibernate Validator**.
- **Exception Handling**: Graceful handling of exceptions with custom error responses.
- **API Documentation**: Automatically generated API documentation using **OpenAPI** and **Swagger UI**.
- **Unit and Integration Testing**: Comprehensive testing for repositories, services, controllers, and exception handlers.
- **Database Profiles**: Supports **H2** for development and **PostgreSQL** for production.

## Technologies Used
```  
- Spring Boot: Backend framework for building the application.
- Spring Data JPA: For database access and repository implementation.
- Hibernate Validator: For data validation.
- H2 Database: In-memory database for development.
- PostgreSQL: Production-ready relational database.
- OpenAPI & Swagger UI: For API documentation.
- Mockito & MockMVC: For unit and integration testing.
- Lombok: For reducing boilerplate code.
- Maven: For dependency management and build automation.
```

## Getting Started
### Prerequisites
- Java 17+
- Maven
- PostgreSQL (if not using H2)

## Setup and Installation
Clone the repository:
   ```sh
   git clone https://github.com/ysebo/Dayardan.git
   cd Dayardan
   ```
### Database-configuration:
### Set profiles to switch between them : prod for PostgreSQL and dev for H2 
For dev(H2 Database)
```sh
  spring:
  profiles:
    active: dev
   ```
For prod(PostgreSQL Database)
```sh
  spring:
  profiles:
    active: prod
   ```
### Run the Application:
### Access Swagger UI:
Open your browser and navigate to:
```
http://localhost:8080/swagger-ui.html
```
---
## API Documentation
The API documentation is automatically generated using **OpenAPI** and can be accessed via **Swagger UI** at:
```
http://localhost:8080/swagger-ui.html
```
## Testing
### Unit Tests
```
- Repository Tests: Test database operations using @DataJpaTest.
- Service Tests: Test business logic using @MockBean and Mockito.
- Controller Tests: Test REST endpoints using MockMvc.
```
### Integration Tests
```
- Test the interaction between layers using @SpringBootTest.
```
### Run tests using:
```
mvn test
```
---

## Endpoints
```
- Interview Sessions
- POST /api/interviews/start/{userId} - Start a new session
- GET /api/interviews/questions/{sessionId} - Retrieve questions for a session
- POST /api/interviews/answer/{sessionId} - Submit an answer

- Questions Management (Admin)
- POST /api/questions/create - Create a new question
- GET /api/questions/get-all - Get all questions
- GET /api/questions//get{id} - Get a specific question
- PUT /api/questions/update/{id} - Update a question
- DELETE /api/questions/get/{id} - Delete a question

  
- Role Management (Admin)
- POST /api/roles/create - Create a new role
- GET /api/roles/get-all - Get all role
- GET /api/roles//get{id} - Get a specific role
- PUT /api/roles/update/{id} - Update a role

  
- Category Management (Admin)
- POST /api/categories/create - Create a new category
- GET /api/categories/get-all - Get all category
- GET /api/categories//get{id} - Get a specific category
- PUT /api/categories/update/{id} - Update a category
```
---
## Exception Handling
### Custom exceptions are handled globally using @RestControllerAdvice. Examples include:
```
- NotFoundException: Returns HTTP 404.
- NotLegalArgumentException: Returns HTTP 400.
```

--
## Data Validation
### Input data is validated using Hibernate Validator. Examples include:
```
- @NotNull, @NotEmpty, @Size, @Email, etc.
```
  
