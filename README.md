# Smart Transport Booking API

CallApp is a backend system developed in Java with Spring Boot and MongoDB, designed for managing transportation routes (airplane, train, bus), reservations, and secure JWT-based user authentication. The system allows complex routing, access control via roles and privileges, and reservation validation.

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://example.com)
[![License](https://img.shields.io/badge/license-MIT-blue)](https://opensource.org/licenses/MIT)
[![Version](https://img.shields.io/badge/version-1.0.0-orange)](https://example.com)

---

## 🛠 Installation

To set up the project locally, follow these steps:

```bash
git clone https://github.com/AMihneaa/callapp.git
cd callapp
./mvnw clean install
./mvnw spring-boot:run
```

### Docker Setup:

```bash
docker build -t callapp .
docker run -p 8080:8080 callapp
```

---

## 🧱 Domain Architecture

### Entities:
- **Abstract Transport** (parent class)
  - Airplane
  - Train
  - Bus
- **Route**: references transportId and contains StopPoints
- **StopPoint**: includes location, arrivalTime, departureTime
- **Reservation**: links users to routes and StopPoints
- **User → Role → Privilege** (Security Layer)

### Structure:
Transport → Routes → StopPoints → Reservations

---

## 🔁 Route Matching and Concatenation Logic

Implemented in `RouteRepositoryImpl`:

### Direct Route Matching
```java
findByDepartureAndArrivalLocation(String departureLocation, String arrivalLocation)
```

Steps:
- Queries all routes containing both locations.
- Filters routes where `departure` appears before `arrival`.
- Excludes reversed or mismatched routes.

### Multi-Hop Concatenation
- Avoids already-used route IDs.
- Applies regex filters to StopPoints.
- Enables recursive chaining for indirect routes.
- Example: `Bucharest → Vienna → Frankfurt → Paris`.

---

## 🔐 Security Layer

### Authentication Flow:
- Register/Login via `/auth/register` or `/auth/login`.
- Receive a JWT token.
- Use `Authorization: Bearer <token>` in requests.
- Verified by a custom `JwtFilter`.

### Role & Privilege Model:
- Each User → has a Role → has a list of Privileges.
- Controllers use annotations for fine-grained access.
- Passwords are encrypted using BCrypt.

### Access Control:

| Endpoint               | Required Access   |
|------------------------|-------------------|
| `/api/airplane (GET)`  | USER, ADMIN       |
| `/api/airplane (POST)` | ADMIN             |
| `/api/reservation`     | Authenticated     |
| `/auth/register`       | Public            |

---

## 🧪 Usage/Examples

### Register:
```bash
curl -X POST http://localhost:8080/auth/register   -H "Content-Type: application/json"   -d '{"username":"testuser", "password":"password123"}'
```

### Login:
```bash
curl -X POST http://localhost:8080/auth/login   -H "Content-Type: application/json"   -d '{"username":"testuser", "password":"password123"}'
```

### Get Routes:
```bash
curl -X GET http://localhost:8080/api/routes   -H "Authorization: Bearer <your_jwt_token>"
```

---

## 📌 Core APIs

| Endpoint                                               | Method | Description              |
|--------------------------------------------------------|--------|--------------------------|
| `/auth/register`                                       | POST   | Register a new user      |
| `/auth/login`                                          | POST   | Login and receive JWT    |
| `/api/routes/{departureLocation}/to/{arrivalLocation}` | GET    | Get available routes     |
| `/api/routes/reserveticket/{ticketId}`                 | POST   | Create a reservation     |


---

## 📈 Future Enhancements

- Swagger/OpenAPI documentation
- React frontend with route visualizer
- Automatic graph-based pathfinding
- Analytics for route popularity and behavior

---


## 👤 Author

**Mihnea Aniculesei**  
GitHub: [https://github.com/AMihneaa](https://github.com/AMihneaa)

---

## 📄 License

This project is licensed under the MIT License.
