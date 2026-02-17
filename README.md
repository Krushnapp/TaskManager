# TaskManager API

A simplified Task Management System built with Java and Spring Boot, following Domain-Driven Design (DDD) principles and Test-Driven Development (TDD) practices.
Tech Stack
Java 17
Spring Boot 3.x
Maven 3.8+
In-memory data store (ConcurrentHashMap)

Steps to run 
git clone https://github.com/Krushnapp/TaskManager.git
cd TaskManager
mvn clean install
mvn spring-boot:run

POST -> /tasks
Request Body:
json{
  "title": "My Task",
  "description": "Some description",
  "status": "PENDING",
  "due_date": "2026-03-01"
}

Get Task —> GET /tasks/{id}
Response 200 OK — returns the task object.
Response 404 Not Found if task does not exist.

Update Task —> PUT /tasks/{id}
Request Body (all fields optional):
json{
  "title": "Updated Title",
  "description": "Updated description",
  "status": "IN_PROGRESS",
  "due_date": "2026-04-01"
}
Delete Task — DELETE /tasks/{id}
Response 204 No Content if successful.
Response 404 Not Found if task does not exist.
