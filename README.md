# Attendance Management System — REST API

A Spring Boot REST API for the Attendance Management System, backed by MySQL,
containerized with Docker, and built/tested automatically via GitHub Actions CI.

## Stack

- Java 17, Spring Boot 3
- Spring Data JPA + MySQL
- JUnit 5 + MockMvc (integration tests, H2 in-memory DB for CI)
- Docker + Docker Compose
- GitHub Actions CI

## Run locally with Docker

```bash
docker-compose up --build
```

The API will be available at `http://localhost:8080`.

## Run without Docker (requires local MySQL + Java 17 + Maven)

```bash
mvn spring-boot:run
```

## Run the tests

```bash
mvn test
```

## API Endpoints

| Method | Endpoint                                  | Description                              |
|--------|--------------------------------------------|-------------------------------------------|
| GET    | `/api/attendance`                          | List all attendance records               |
| GET    | `/api/attendance/{id}`                     | Get a single record by ID                 |
| GET    | `/api/attendance/student/{studentId}`      | Get all records for a student             |
| GET    | `/api/attendance/student/{studentId}/percentage` | Get a student's attendance percentage |
| POST   | `/api/attendance`                          | Create a new attendance record            |
| DELETE | `/api/attendance/{id}`                     | Delete a record by ID                     |

### Example request

```bash
curl -X POST http://localhost:8080/api/attendance \
  -H "Content-Type: application/json" \
  -d '{"studentName":"Souvik Ghorai","studentId":"STU001","date":"2026-09-18","status":"PRESENT"}'
```

## Next steps

- Wire this API up to the existing HTML/CSS/JS frontend from the original
  Attendance Management System project.
- Add authentication (e.g. Spring Security + JWT) for the login flow.
- Add pagination to `GET /api/attendance` once record counts grow.
