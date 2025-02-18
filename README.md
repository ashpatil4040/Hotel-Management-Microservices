# Hotel Management Microservices

A microservices-based architecture implementing a hotel management system with multiple independent services.

## Architecture Overview

### Core Services
- **User Service**: Handles user management and profiles
- **Hotel Service**: Manages hotel inventory and information
- **Rating Service**: Handles customer ratings and reviews

### Technical Stack

#### Spring Cloud Components
- Service Registry (Eureka)
- API Gateway
- Config Server
- Circuit Breaker (Resilience4j) [Planned]

#### Security
- Okta Authentication [Planned]

#### Databases
- MySQL: User Service
- PostgreSQL: Hotel Service
- MongoDB: Rating Service

#### Tools & Technologies
- Spring Boot 3.x
- Spring Data JPA
- Spring Security
- Lombok
- Spring Cloud
- Maven
- REST APIs

## Service Details

### User Service
- User management
- CURD operations
- MySQL database integration
- JPA implementation

### Hotel Service
- Hotel inventory management
- CURD operations
- PostgreSQL database
- REST endpoints for hotel operations

### Rating Service
- Customer feedback system
- Rating CURD operations
- MongoDB integration

## Features

- Distributed System Architecture
- Service Discovery
- Database Per Service
- Centralized Configuration
- API Gateway Implementation
- Microservices Security [Planned]

## Getting Started

### Prerequisites
- JDK 17 or later
- Maven 3.6+
- MySQL
- PostgreSQL
- MongoDB

### Installation Steps

1. Clone the repository

git clone https://github.com/yourusername/Hotel-Management-Microservices.git

