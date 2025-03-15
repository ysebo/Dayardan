# Interview Preparation System

## Overview
The Interview Preparation System is a Spring Boot-based backend application designed to facilitate interview sessions with multiple-choice questions. It allows users to start interview sessions, retrieve session questions, and submit answers while tracking correctness.

## Features
- Dynamic interview session creation
- Randomized question selection
- Answer submission and validation
- Role-based access control
- Swagger UI for API documentation

## Technologies Used
- **Spring Boot** (Spring MVC, Spring Security, Spring Data JPA)
- **H2 & PostgreSQL** (Profile-based database setup)
- **Lombok & MapStruct** (For clean and efficient code)
- **Swagger UI** (API documentation)

## Getting Started
### Prerequisites
- Java 17+
- Maven
- PostgreSQL (if not using H2)

### Setup Instructions
1. Clone the repository:
   ```sh
   git clone https://github.com/ysebo/Dayardan.git
   cd Dayardan
   ```
2. Configure the database in application.properties:
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
3. Build and run the application:

### API Endpoints

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
