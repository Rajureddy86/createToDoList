# Todo Management Spring Boot Project

This project implements a simple Todo management REST API using Spring Boot. The API provides endpoints for creating, retrieving, updating, and deleting Todo items.

## Requirements

- **Get All Todos**: 
  - `GET /todos` - Retrieves a list of all todos from the database.

- **Create Todo**: 
  - `POST /todos` - Allows the creation of a new todo item.

- **Update Todo by ID**: 
  - `PATCH /todos/{id}` - Updates the details of an existing todo item specified by its ID.

- **Delete Todo by ID**: 
  - `DELETE /todos/{id}` - Deletes a todo item from the database using its ID.

- **Get Todo by ID**: 
  - `GET /todos/{id}` - Fetches the details of a single todo item by its unique identifier.

## Testing

- **Unit Testing**:
  - Unit tests are implemented using JUnit and Mockito. Each endpoint has corresponding test cases to ensure the functionality works as intended.

- **Integration Testing**:
  - Integration tests are performed using Spring Boot Test. These tests interact with the SQLite database to ensure that all parts of the application work together correctly.

## Database

- **SQLite**: 
  - The project uses SQLite as the database for both development and testing. It is a lightweight, file-based database, which simplifies the configuration and deployment processes.
  
- **Schema Management**:
  - Database schemas are managed through `schema.sql` files, which are automatically executed by Spring Boot to set up the necessary tables in the SQLite database for both test and development environments.

## Postman Collection

- A Postman collection for the Todo management API is provided to facilitate endpoint testing and interaction.

## How to Run

- To run the application, use the following command:
  ```shell
  ./mvnw spring-boot:run
