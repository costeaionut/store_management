# Store Management API

## Overview

This is a Spring Boot based project built with Java 17 and Spring Boot Framework 3.4.0. It provides the following
functionalities:

1. Authentication and Authorization
    - Basic authentication system with JWT token generation and validation.
    - Role-based access with three main roles: USER, ADMIN, and INVENTORY_MANAGER

2. Product Management API
    - Product management API that supports CRUD operations.
    - Role restricted access for certain operations.
    - Logs for product operations and queries.

For all incoming data we make sure to validate and sanitize the input before adding to the database.

## Technologies user

- **Java 17**.
- **Maven 3.9.5**
- **Spring Boot Framework 3.4.0**.
- **Spring Security** for authorization and role based protection.
- **H2 Database** for information storage and ease of testing setup.
- **Spring Data JPA** for simplified data access and persistence.
- **JJWT** version 0.12.6 for JWT generation and validation.

## Installation and setup

1. **Prerequisites**:
    - Java 17
    - Maven 3.9.5
2. **Clone the repository**

```shell
git clone https://github.com/costeaionut/store_management.git
cd store_management
```

3. Build the project

```shell
mvn clean install
```

4. Run the application

```shell
mvn spring-boot:run
```

- To access the API use the default base URL `http://localhost:8080`

## API Endpoints

### Authentication endpoints

- **Register** endpoint that takes the user information and creates an account
    - **Type**: POST
    - **Path**: `/api/auth/register`
    - **Access**: All roles
    - **Body**:
      ```json
      {
        "name": "string",
        "surname": "string",
        "email": "string",
        "password": "string",
        "role": "USER | ADMIN | INVENTORY_MANAGER"
      }
      ```
- Login endpoint that takes the user credentials and returns a JWT token
    - **Type**: POST
    - **Path**: `/api/auth/login`
    - **Access**: All roles
    - **Body**:
      ```json
      {
        "email": "string",
        "password": "string"
      }
      ```
    - **Response**: JWT Token

### Production management endpoints (WIP)

- Retrieve all products endpoint:
    - **Type**: GET
    - **Path**: `/api/product/`
    - **Access**: All roles
    - **Parameters**: Optional filters list
    - **Response**:
      ```json
      {
        "products": [
          {
            "name": "string",
            "description": "string",
            "price": "double",
            "category": "ELECTRONICS | GROCERY |CLOTHING",
        
            "electronicProperties":{
              "brand": "string",
              "warranty": "int",
              "powerRequirement": "string"
            },
      
            "groceryProperties":{
              "expiryDate": "string",
              "weight": "double",
              "isPerishable": "boolean"
            },
        
            "clothing":{
              "size": "XXS | XS | S | M | L | XL | XXL",
              "material": "string",
              "color": "string"
            }
          },
          {
            "...": "..."
          }      
        ] 
      }
      ```


- Retrieve specific product:
    - **Type**: GET
    - **Path**: `/api/product/{id}`
    - **Access**: All roles
    - **Parameters**: id of the product
    - **Response**:
      ```json
      {
        "name": "string",
        "description": "string",
        "price": "double",
        "category": "ELECTRONICS | GROCERY |CLOTHING",
        
        "electronicProperties":{
          "brand": "string",
          "warranty": "int",
          "powerRequirement": "string"
        },
      
        "groceryProperties":{
          "expiryDate": "string",
          "weight": "double",
          "isPerishable": "boolean"
        },
        
        "clothing":{
          "size": "XXS | XS | S | M | L | XL | XXL",
          "material": "string",
          "color": "string"
        }
      } 
      ```
- Add product:
    - **Type**: POST
    - **Path**: `/api/product/add`
    - **Access**: `ADMIN` or `INVENTORY_MANAGER`
    - **Body**:
      ```json
      {
        "name": "string",
        "description": "string",
        "price": "double",
        "category": "ELECTRONICS | GROCERY |CLOTHING",
        
        "electronicProperties":{
          "brand": "string",
          "warranty": "int",
          "powerRequirement": "string"
        },
      
        "groceryProperties":{
          "expiryDate": "string",
          "weight": "double",
          "isPerishable": "boolean"
        },
        
        "clothing":{
          "size": "XXS | XS | S | M | L | XL | XXL",
          "material": "string",
          "color": "string"
        }
      } 
      ```
    - **Response**: Created product


- Update product:
    - **Type**: POST
    - **Path**: `/api/product/update`
    - **Access**: `ADMIN` or `INVENTORY_MANAGER`
  - **Body**:
    ```json
    {
      "name": "string",
      "description": "string",
      "price": "double",
      "category": "ELECTRONICS | GROCERY |CLOTHING",
      
      "electronicProperties":{
        "brand": "string",
        "warranty": "int",
        "powerRequirement": "string"
      },
    
      "groceryProperties":{
        "expiryDate": "string",
        "weight": "double",
        "isPerishable": "boolean"
      },
      
      "clothing":{
        "size": "XXS | XS | S | M | L | XL | XXL",
        "material": "string",
        "color": "string"
      }
    } 
    ```
    - **Response**: Updated product

- Delete product:
    - **Type**: POST
    - **Path**: `/api/product/delete/{id}`
    - **Access**: `ADMIN` or `INVENTORY_MANAGER`
    - **Parameters**: id of the product

### Logs management (WIP)
- Retrieve product operation logs
  - **Type**: GET
  - **Path**: `/api/logs/`
  - **Access**: `ADMIN`
  - **Parameters**: Optional filters list
  - **Response**:
    ```json
        {
          "logs": [
            { 
              "operator": "string",
              "product": "int",
              "date": "string",
              "operation": "string"
            },
            {
              "...": "..."
            }
          ]   
        } 
    ```
