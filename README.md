# Online Traineeship Management Application - Reengineering Project

## Project Overview
This project focuses on the **reengineering** and functional extension of a legacy Java-based web application. The system is designed for a University Traineeship Committee to manage and monitor traineeship positions, involving students, companies, and supervising professors.

The main objective was to transform a "smelly" legacy codebase into a maintainable, extensible, and well-tested system by applying modern software engineering principles and design patterns.

## Key Reengineering Tasks
* **Decomposition of God Controller**: The central `TraineeshipAppController`, which originally handled almost all application logic, was split into specific, actor-based controllers (`StudentController`, `CompanyController`, `ProfessorController`, `CommitteeController`) to improve separation of concerns.
* **Introduction of Service Layer**: Business logic was moved from controllers to a new Service Layer (e.g., `StudentServiceImpl`, `CompanyServiceImpl`), ensuring that controllers only handle request routing and view management.
* **Strategy & Template Method Patterns**: To reduce code duplication in traineeship searches and supervisor assignments, we implemented abstract base classes and utilized the Strategy and Template Method patterns.
* **Encapsulation & Domain Logic**: Domain objects were refactored from simple data containers to active objects that manage their own state (e.g., `addEvaluation`, `addPosition`), preventing external mutation of internal collections.

## Tech Stack
* **Language**: Java 17 
* **Framework**: Spring Boot 3.x (Spring MVC, Spring Security, Spring Data JPA) 
* **Database**: MySQL 
* **Build Tool**: Maven 

## Installation & Setup
1. **Database**: Ensure MySQL is running on port `3306`. Create a database using the provided SQL script.
2. **Configuration**: Update `src/main/resources/application.properties` with your database credentials (username, password, and URI)[cite: 1].
3. **Build**: Run `mvn clean install` to resolve dependencies and build the project.
4. **Run**: Execute the application through your IDE or via `mvn spring-boot:run`.The application defaults to `localhost:8080`.
