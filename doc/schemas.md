# · WRK2025-USUARIOS – API Schemas

This file documents the data models used in request and response bodies across the API.  
Each schema defines the structure and types of JSON objects used by the endpoints.

---

## · ChangePasswordRequest

Used to request a password change for a user.

```json
{
  "oldPassword": "string",
  "newPassword": "string"
}
```

---

## · Address

Used to update a user's address information.

```json
{
  "country": "string",
  "zipCode": "string",
  "city": "string",
  "street": "string"
}
```

---

## · UserRequest

Used when registering a new user.

```json
{
  "name": "string",
  "email": "string",
  "plainPassword": "string"
}
```

---

## · Email

Encapsulates a valid email value.

```json
{
  "value": "string"
}
```

---

## · FavoriteId

Represents a product marked as a favorite by the user.

```json
{
  "value": 123456
}
```

---

## · LoyaltyPoints

Represents the loyalty points earned by a user.

```json
{
  "value": 100
}
```

---

## · Password

Represents the hashed value of a user's password.

```json
{
  "hashedValue": "string"
}
```

---

## · User

Represents a complete user entity with all related fields.

```json
{
  "id": {
    "uuid": "string"
  },
  "email": {
    "value": "string"
  },
  "name": "string",
  "password": {
    "hashedValue": "string"
  },
  "address": {
    "country": "string",
    "zipCode": "string",
    "city": "string",
    "street": "string"
  },
  "favoriteProductIds": [
    { "value": 101 },
    { "value": 202 }
  ],
  "loyaltyPoints": {
    "value": 250
  },
  "disabled": false
}
```

---

## · UserId

Represents the UUID identifier of a user.

```json
{
  "uuid": "string"
}
```

---

## · NotificationDto

Used to deliver a notification to the user.

```json
{
  "message": "string",
  "createdAt": "2025-05-22T13:45:00Z",
  "important": true
}
```

---