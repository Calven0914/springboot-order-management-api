# Order Management API

This project is a small Spring Boot REST API designed for interview showcasing. It demonstrates common backend patterns a fresh graduate should be able to explain clearly:

- layered architecture: controller, service, repository
- REST endpoints with proper HTTP verbs
- DTO-based request and response handling
- validation using Bean Validation annotations
- JPA entity relationships
- global exception handling
- simple business logic for stock checking and order total calculation

## Business story

The system manages:

- customers
- products
- purchase orders

When an order is created, the application:

1. validates that the customer exists
2. validates that all requested products exist
3. checks available stock
4. calculates line totals and the order total
5. reduces product stock
6. stores the order with status `CREATED`

## Tech stack

<img width="635" height="356" alt="image" src="https://github.com/user-attachments/assets/a520aa2d-271d-4423-b48b-cd87f6d1e17c" />

- Java 11
- Spring Boot 2.7
- Spring Web
- Spring Data JPA
- H2 database
- Bean Validation
- JUnit / Spring Boot Test

## Architecture

`controller`

- receives HTTP requests
- returns JSON responses

`service`

- contains business logic
- validates workflow rules

`repository`

- communicates with the database

`entity`

- represents database tables and relationships

`dto`

- shapes API input and output cleanly without exposing entities directly

## Main endpoints

- `POST /api/customers`
- `GET /api/customers`
- `POST /api/products`
- `GET /api/products`
- `POST /api/orders`
- `GET /api/orders`
- `GET /api/orders/{id}`
- `PATCH /api/orders/{id}/status`

## Example order payload

```json
{
  "customerId": 1,
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ]
}
```

## Interview explanation you can say

"I built an Order Management API using Spring Boot. I structured it with controller, service, and repository layers to separate concerns. The project exposes RESTful endpoints for managing customers, products, and orders. The most important business flow is order creation, where the system validates customer and product data, checks stock availability, calculates the total amount, updates inventory, and saves the order. I used DTOs for cleaner API contracts and global exception handling for consistent error responses."

## How to run

This workspace currently has Java 11 available, but Maven is not installed. Once Maven is available, run:

```bash
mvn spring-boot:run
```

Or run tests with:

```bash
mvn test
```

H2 console:

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:ordermgmt`
- username: `sa`
- password: `password`

Swagger UI:

- URL: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Next upgrades for microservices

This project is intentionally built as a clean monolith first. A strong interview story is that you would split it into:

- `customer-service`
- `catalog-service`
- `order-service`
- `api-gateway`
- `discovery-server`

That lets you explain both REST API fundamentals and the path toward microservices.
