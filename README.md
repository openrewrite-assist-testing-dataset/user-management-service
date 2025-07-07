# User Management Service

A Dropwizard-based RESTful web service for managing users with PostgreSQL database and basic authentication.

## Features

- User CRUD operations (Create, Read, Update, Delete)
- Basic HTTP authentication with BCrypt password hashing
- PostgreSQL database with Flyway migrations
- RESTful API endpoints
- Comprehensive test coverage with JUnit 4
- Docker support
- Kubernetes deployment with Helm charts

## Prerequisites

- Java 11
- PostgreSQL 13+
- Gradle 6.9+
- Docker (optional)

## Quick Start

### 1. Setup Database

```bash
# Create PostgreSQL database
createdb usermanagement

# Or using Docker
docker run --name postgres -e POSTGRES_DB=usermanagement -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres:13
```

### 2. Build and Run

```bash
# Build the application
./gradlew build

# Run database migrations
./gradlew flywayMigrate

# Run the application
./gradlew run
```

The application will start on:
- Application server: http://localhost:8080
- Admin server: http://localhost:8081

### 3. Test the API

Default admin user credentials:
- Username: `admin`
- Password: `admin123`

```bash
# Get all users
curl -u admin:admin123 http://localhost:8080/users

# Create a new user
curl -u admin:admin123 -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","passwordHash":"password123"}'

# Get user by ID
curl -u admin:admin123 http://localhost:8080/users/1
```

## API Endpoints

- `GET /users` - Get all users
- `GET /users/{id}` - Get user by ID
- `POST /users` - Create new user
- `PUT /users/{id}` - Update user
- `DELETE /users/{id}` - Delete user

## Configuration

Configuration is managed through `src/main/resources/config.yml`:

```yaml
database:
  url: jdbc:postgresql://localhost:5432/usermanagement
  user: postgres
  password: postgres
```

## Testing

```bash
# Run all tests
./gradlew test

# Run tests with coverage
./gradlew test jacocoTestReport
```

## Docker

```bash
# Build Docker image
docker build -t user-management-service .

# Run with Docker
docker run -p 8080:8080 -p 8081:8081 user-management-service
```

## Kubernetes Deployment

```bash
# Deploy with Helm
helm install user-management-service ./helm/user-management-service

# Or with custom values
helm install user-management-service ./helm/user-management-service -f custom-values.yaml
```

## Technology Stack

- **Framework**: Dropwizard 2.0.25
- **Java**: 11
- **Database**: PostgreSQL with Flyway migrations
- **Authentication**: Basic Auth with BCrypt
- **Testing**: JUnit 4, Mockito
- **Build Tool**: Gradle 6.9
- **Logging**: Log4j2
- **Containerization**: Docker
- **Orchestration**: Kubernetes with Helm

## Development

This application uses older versions of dependencies for testing purposes:
- Dropwizard 2.0.x (older version)
- Jackson 2.12.x (older version)
- Guava 30.1.x (older version)
- GitHub Actions with v2 actions (outdated)

## License

This project is created for testing purposes as part of the OpenRewrite assist testing dataset.