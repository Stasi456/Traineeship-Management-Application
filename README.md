# Online Traineeship Management Application - Reengineering Project

## Project Overview
[cite_start]This project focuses on the **reengineering** and functional extension of a legacy Java-based web application[cite: 4, 5]. [cite_start]The system is designed for a University Traineeship Committee to manage and monitor traineeship positions, involving students, companies, and supervising professors[cite: 4].

[cite_start]The main objective was to transform a "smelly" legacy codebase into a maintainable, extensible, and well-tested system by applying modern software engineering principles and design patterns.

## Key Reengineering Tasks
* [cite_start]**Decomposition of God Controller**: The central `TraineeshipAppController`, which originally handled almost all application logic, was split into specific, actor-based controllers (`StudentController`, `CompanyController`, `ProfessorController`, `CommitteeController`) to improve separation of concerns.
* [cite_start]**Introduction of Service Layer**: Business logic was moved from controllers to a new Service Layer (e.g., `StudentServiceImpl`, `CompanyServiceImpl`), ensuring that controllers only handle request routing and view management.
* [cite_start]**Strategy & Template Method Patterns**: To reduce code duplication in traineeship searches and supervisor assignments, we implemented abstract base classes and utilized the Strategy and Template Method patterns.
* [cite_start]**Encapsulation & Domain Logic**: Domain objects were refactored from simple data containers to active objects that manage their own state (e.g., `addEvaluation`, `addPosition`), preventing external mutation of internal collections.

## Implemented User Stories
[cite_start]We implemented several previously missing features from the requirements backlog[cite: 4, 5]:
* [cite_start]**US6 (Student)**: Fill in a traineeship logbook to report activities[cite: 3, 5].
* [cite_start]**US9 (Company)**: Access the list of traineeship positions assigned to students[cite: 3, 5].
* [cite_start]**US11 (Company)**: Delete unassigned traineeship positions[cite: 3, 5].
* [cite_start]**US12 (Company)**: Fill in student evaluations (scale 1-5)[cite: 3, 5].
* [cite_start]**US14 (Professor)**: Access the list of supervised traineeship positions[cite: 3, 5].
* [cite_start]**US15 (Professor)**: Fill in evaluations for both the student and the hosting company[cite: 3, 5].

## Tech Stack
* [cite_start]**Language**: Java 17 
* [cite_start]**Framework**: Spring Boot 3.x (Spring MVC, Spring Security, Spring Data JPA) 
* [cite_start]**Database**: MySQL 
* [cite_start]**Build Tool**: Maven 
* [cite_start]**Testing**: JUnit, Mockito, Spring Boot Test 
* [cite_start]**View Engine**: Thymeleaf [cite: 1]

## Testing Suite
[cite_start]The project includes a comprehensive suite of automated tests to ensure system reliability:
* [cite_start]**Controller Tests**: Validating endpoints, model attributes, and view redirects.
* [cite_start]**Service Tests**: Testing business logic, repository interactions, and constraint enforcement.
* [cite_start]**Domain Tests**: Verifying object initialization, bidirectional relationship management, and defensive copying.

## Installation & Setup
1. **Database**: Ensure MySQL is running on port `3306`. [cite_start]Create a database using the provided SQL script[cite: 1].
2. [cite_start]**Configuration**: Update `src/main/resources/application.properties` with your database credentials (username, password, and URI)[cite: 1].
3. [cite_start]**Build**: Run `mvn clean install` to resolve dependencies and build the project[cite: 1].
4. **Run**: Execute the application through your IDE or via `mvn spring-boot:run`. [cite_start]The application defaults to `localhost:8080`[cite: 1].
