# WRK2025-USUARIOS API Endpoints

This service provides RESTful endpoints for managing users, their favorite products, and notifications.  
It follows a Domain-Driven Design (DDD) architecture.
## Base URL http://wrk25uwuarios.dpdns.org/api/v1

---

## · Users

| Method | Endpoint                         | Description                      |
|--------|----------------------------------|----------------------------------|
| POST   | `/users`                         | Register a new user              |
| GET    | `/users/{id}`                    | Get user by ID                   |
| DELETE | `/users/{id}`                    | Delete a user                    |
| PUT    | `/users/{id}/change-name`        | Change user’s name               |
| PUT    | `/users/{id}/change-email`       | Change user’s email              |
| PUT    | `/users/{id}/change-password`    | Change user’s password           |
| PUT    | `/users/{id}/change-address`     | Change user’s address            |
| GET    | `/users/{id}/loyalty-points`     | Get user’s loyalty points        |

---

## · Favorites

| Method | Endpoint                                      | Description                              |
|--------|-----------------------------------------------|------------------------------------------|
| GET    | `/users/{id}/favorite-products`               | Get user's favorite product IDs          |
| PUT    | `/users/{id}/favorite-products/add`           | Add a product to user's favorites        |
| PUT    | `/users/{id}/favorite-products/remove`        | Remove a product from user's favorites   |
| GET    | `/users/favorite-product/{productId}`         | Get all user IDs who favorited a product |

---

## · Notifications

| Method | Endpoint                                           | Description                            |
|--------|----------------------------------------------------|----------------------------------------|
| GET    | `/users/{id}/notifications`                        | Get all notifications for a user       |
| PATCH  | `/notifications/{notificationId}`                  | Update importance of a notification    |
| DELETE | `/notifications/{notificationId}`                  | Delete a notification by ID            |

---

## · Event-Driven Features

- Publishes domain events (e.g. `UserDisabledEvent`) via RabbitMQ.
- Consumes events (e.g. product updates) to generate user notifications.

---
## · Swagger UI

After starting the app, browse:
http://wrk25uwuarios.dpdns.org/swagger-ui/index.html
to explore and test the API.

---
