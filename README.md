# Order Management API

REST API for customer, product, and order management built with Java and Spring Boot.

## Tech Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- JSON, XML
- JUnit 5
- Maven

## Features

- Customer CRUD
- Product CRUD
- Order creation
- Add products to orders
- Stock validation
- Total amount calculation
- Order status update
- Export orders in JSON and XML
- Global exception handling
- Unit and controller tests

## Project Structure

- `controller` → REST endpoints
- `service` → business logic
- `repository` → data access
- `entity` → JPA entities
- `dto` → request/response models
- `exception` → custom exceptions and global handler

## Main Endpoints

### Customers
- `POST /api/customers`
- `GET /api/customers`
- `GET /api/customers/{id}`
- `PUT /api/customers/{id}`
- `DELETE /api/customers/{id}`

### Products
- `POST /api/products`
- `GET /api/products`
- `GET /api/products/{id}`
- `PUT /api/products/{id}`
- `DELETE /api/products/{id}`

### Orders
- `POST /api/orders`
- `GET /api/orders`
- `GET /api/orders/{id}`
- `POST /api/orders/{orderId}/items`
- `PATCH /api/orders/{id}/status`

### Export
- `GET /api/orders/{id}/export`
- `GET /api/orders/{id}/export/xml`

## Business Rules

- Orders must belong to an existing customer
- Only active products can be added to an order
- Stock must be sufficient before adding items
- Product stock is reduced when items are added
- Order total is recalculated automatically
- Cancelled or shipped orders cannot be modified depending on state rules

## Running the Project

### 1. Create a PostgreSQL database:

``sql
CREATE DATABASE order_management;


### 2. Configure application.properties with your database credentials


### 3. Run the application:
   mvn spring-boot:run


### 4. API base URL:
http://localhost:8080


## Running Tests
mvn test



### Sample JSON Requests
## Create customer

{
"name": "Juan Perez",
"email": "juan@example.com",
"phone": "600123123"
}


## Create product

{
"name": "Mechanical Keyboard",
"description": "RGB keyboard",
"price": 79.99,
"stock": 10,
"active": true
}


## Create order

{
"customerId": 1
}


## Add item to order

{
"productId": 1,
"quantity": 2
}


## Testing

The project includes:
- unit tests for services
- controller test for product endpoint
- business rule validation tests



## Author

Daniel
