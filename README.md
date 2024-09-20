# Spring Boot Redis Demo Project

This project is a simple Spring Boot application that demonstrates how to use Redis for caching with a MySQL database
for persistence. It provides RESTful APIs for creating, retrieving, and caching user data.

## Features

- **Spring Boot 3.3.3**
- **Java 17**
- **MySQL Database**
- **Redis for caching**
- **RESTful API to manage users**

## Requirements

- Java 17+
- Maven
- MySQL
- Redis

## Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/your-username/spring-boot-redis-demo.git
cd spring-boot-redis-demo
```

# 2. Configure MySQL and Redis

Make sure that MySQL and Redis are installed and running on your machine. Update the application.properties file with
your MySQL and Redis connection details.

**MySQL configuration**

```
spring.datasource.url=jdbc:mysql://localhost:3306/redis
spring.datasource.username=root
spring.datasource.password=Admin@123
```

**Redis configuration**

```
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

# 3. Create MySQL Database

`CREATE DATABASE redis;`

# 4. Build and run the application

You can use Maven to build and run the application.

```bash
mvn clean install
mvn spring-boot:run
```

# 5. Access the application

The application runs on port 9091 by default.

* Get all users: `GET http://localhost:9091/user`
* Get user by ID: `GET http://localhost:9091/user/{userId}`
* Create a user: `POST http://localhost:9091/user/save`

Sample request body for creating a user:

```
{
"name": "John Doe",
"email": "john.doe@example.com"
}
```

# Redis Caching Logic

* Fetch a user by ID: The system first checks if the user is cached in Redis. If found, it returns the cached user. If
  not, it fetches from the database, caches the result in Redis for 30 seconds, and then returns it.
* Create a new user: The user is saved in the MySQL database and cached in Redis for 30 seconds.
* Fetch all users: Fetches all users from the database and stores the result in Redis for 30 seconds.

# Dependencies

The key dependencies used in this project include:

* Spring Boot Starter Web: For building RESTful web services.
* Spring Boot Starter Data JPA: For interacting with MySQL.
* Spring Boot Starter Data Redis: For caching with Redis.
* MySQL Connector: For MySQL integration.
* Lombok: For reducing boilerplate code (optional).

Refer to **pom.xml** for the complete list of dependencies.

# Project Structure

* **Controller**: Handles HTTP requests and responses.
    * **UserController**: Exposes REST endpoints for user operations.
* **Service**: Contains business logic.
    * **UserService**: Handles user retrieval, saving, and cache updates.
    * **RedisService**: Manages interactions with Redis (get/set).
* **Repository**: Manages data persistence.
    * **UserRepository**: Extends JPA repository for MySQL interaction.
* **Configuration**: Spring configuration files.
    * **RedisConfig**: Configures RedisTemplate for Redis interactions.