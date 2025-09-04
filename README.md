# **Verimly API**

Verimly is a backend service that enables users to efficiently manage their tasks and the time they spend on them. It is built with a modern, modular, and testable architecture.

### **✨ Key Features**
- **User Management**: Registration, login, and logout operations.

- **Task Management**: Create, list, update, and delete tasks.

- **Folder Management**: Organize tasks under folders.

- **Time Tracking**: Start, pause, and stop work sessions for tasks.

- **Security**: Secure authentication with JWT (JSON Web Token) and HttpOnly cookies.

- **API Documentation**: Automatically generated interactive API documentation with SpringDoc OpenAPI.

- **Multilingual Support**: English and Turkish language support for error messages.


### **🛠️ Technologies Used**
- **Backend**: Java 21, Spring Boot 3

- **Database**: PostgreSQL, H2 (for testing)

- **Data Access**: Spring Data JPA, Hibernate

- **Security**: Spring Security, JWT

- **Build & Dependency Management**: Maven

- **Testing**: JUnit 5, Mockito, Testcontainers

- **API Documentation**: SpringDoc OpenAPI

- **Others**: Lombok, MapStruct


### **🏗️ Architecture**

The project is a multi-module monolithic structure designed according to Hexagonal (Ports & Adapters) architecture principles.  
This structure isolates the business logic (domain and application layers) from the outside world (web, database, etc.), providing high testability and flexibility.

- **commons-core**: Core classes, exceptions, and security infrastructure shared across all modules.

- **user**: Contains the business logic related to user management.

- **task**: Contains the business logic for task, folder, and session management.

- **api-composition**: Combines services from different modules to expose new APIs and manages overall security configuration.

- **app**: The main module that brings all modules together and bootstraps the Spring Boot application.


### **🚀Getting Started Guide**
**Prerequisites**

- Git
- Java 21 (Temurin veya eşdeğeri)
- Apache Maven 3.9+
- Docker (isteğe bağlı, Docker ile çalıştırma için)

### **Installation & Running (Local)**
**1. Clone the project:**

  ```
  git clone https://github.com/muhsener-be/verimly-be.git verimly-be
  cd verimly-be
  ```

**2. Create the configuration file:**
* In the root directory of the project, create a file named .env and fill it in by referring to the contents of the .env.example file.

**3. Compile project and install dependencies:**

  ```
  mvn clean install
  ```


**4. Run the app:**

```
 java -jar app/target/app-0.0.1-SNAPSHOT.jar
```
_Project will start on port `8080` by default._


### 🐳 Docker ile Çalıştırma
Project is available in DockerHub on image `muhsener98/verimly-be-api:latest`

1. **Pull docker image:**

```
docker pull muhsener98/verimly-be-api:latest
```


2. **Run the container:**

Before running the following command, make sure to add all variables from [the table](#-configuration-environment-variables) to your command using the -e parameter.
```
docker run -p 8080:8080 \
-e DB_URL="your_database_url_here" \
-e DB_USERNAME="your_database_username" \
-e DB_PASSWORD="your_database_password" \
-e PROFILE="prod" \
-e FE_ORIGIN="[https://verimly-fe-abc.vercel.app](https://verimly-fe-abc.vercel.app)" \
-e SPRING_SQL_INIT_MODE="never" \
-e BOOTSTRAP_USER_FIRSTNAME="YourFirstName" \
-e BOOTSTRAP_USER_LASTNAME="YourLastName" \
-e BOOTSTRAP_USER_EMAIL="your_email@example.com" \
-e BOOTSTRAP_USER_PASSWORD="your_strong_password" \
-e JWT_SECRET_KEY="your_super_secret_jwt_key" \
muhsener98/verimly-be-api:latest
```

### ⚙️ Configuration (Environment Variables)
The following environment variables must be set for the application to run.

| Variable                   | Description                                                        | Example Value                                                  |
|-----------------------------|--------------------------------------------------------------------|----------------------------------------------------------------|
| **DB_URL**                  | PostgreSQL database connection URL.                               | `jdbc:postgresql://ep-still-resonance.../neondb?sslmode=require` |
| **DB_USERNAME**             | Database username.                                                 | `neondb_owner`                                                 |
| **DB_PASSWORD**             | Database password.                                                 | `your_password`                                                |
| **FE_ORIGIN**               | Allowed frontend origin for CORS.                                  | `https://verimly-fe-abc.vercel.app`                            |
| **PROFILE**                 | Active Spring profile (`dev` or `prod`).                           | `prod`                                                         |
| **SPRING_SQL_INIT_MODE**    | Whether to run `schema.sql` at application startup.                | `never`                                                        |
| **BOOTSTRAP_USER_FIRSTNAME**| First name of the default bootstrap user.                          | `Muhammet`                                                     |
| **BOOTSTRAP_USER_LASTNAME** | Last name of the default bootstrap user.                           | `Şener`                                                        |
| **BOOTSTRAP_USER_EMAIL**    | Email of the default bootstrap user.                               | `muhsener@verimly.com`                                         |
| **BOOTSTRAP_USER_PASSWORD** | Password of the default bootstrap user.                            | `password`                                                     |
| **CORS_MAX_AGE**            | Max age (in seconds) for CORS preflight request caching.           | `3600`                                                         |
| **JWT_EXPIRES_IN**          | Expiration time for JWT tokens (in milliseconds).                  | `86400000`                                                     |
| **COOKIE_SAME_SITE**        | SameSite attribute for cookies (`None`, `Lax`, or `Strict`).       | `None`                                                         |
| **COOKIE_MAX_AGE**          | Cookie expiration time (in seconds).                               | `86400`                                                        |
| **COOKIE_DOMAIN**           | Domain attribute for cookies (leave empty for default).             | *(empty)*                                                      |
| **JWT_SECRET_KEY**          | Secret key used to sign JWT tokens.                                | `a7gH!asdasdaksjdaksd1234`                                     |


### **📖 API Documentation**
After starting the application, you can access the interactive API documentation (Swagger UI) at the following address:
http://localhost:8080/swagger-ui.html
