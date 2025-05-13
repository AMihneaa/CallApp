CallApp – Smart Transport Booking API

CallApp is a backend system developed in Java with Spring Boot and MongoDB, designed for managing transportation routes (airplane, train, bus), reservations, and secure JWT-based user authentication. The system allows complex routing, access control via roles and privileges, and reservation validation.

1. Domain Architecture: Transport → Routes → StopPoints → Reservations

Transport Models:
The system defines three transport types as independent entities:
- Airplane.java
- Train.java
- Bus.java

These entities represent transport metadata (transportId, seat count, type) and are referenced by the Route entity through the transportId field.

Route and StopPoints:
Each Route includes:
- transportId: a reference to a transport instance
- transportType: enum (AIRPLANE, TRAIN, BUS)
- availableSeats: remaining capacity
- List<StopPoint>: an ordered list of location-based checkpoints

Each StopPoint contains:
- location (string)
- arrivalTime (optional)
- departureTime (optional)

2. Route Matching and Concatenation Logic

Routes are stored in MongoDB as documents containing stop point sequences. The application supports intelligent route searching using:

Direct Route Matching:
Implemented in:
findByDepartureAndArrivalLocation(String departureLocation, String arrivalLocation)

Steps:
- Queries all routes containing both locations.
- Filters only those where departure appears before arrival in the list.
- Ignores routes where the stop order is reversed or locations are missing.

Multi-Hop Concatenation (via findRestRoute):
This method supports chaining multiple routes to simulate a longer journey when no direct route exists.

Steps:
- Avoids already-used route IDs (nin clause).
- Applies regex filters on stop point locations to match possible partial segments.
- Can be used recursively in frontend to chain multiple valid segments together.

This enables the creation of a route like:
Bucharest → Vienna → Frankfurt → Paris
even if no single route contains all of them.

3. Security Layer

The system uses full role-based security using Spring Security and JWT tokens.

Authentication Flow:
- A user registers or logs in via /auth/register and /auth/login.
- Upon login, they receive a JWT token.
- The token is then attached to the Authorization: Bearer <token> header in future requests.
- A custom JwtFilter verifies the token on each request.

Role and Privilege Model:
- Each User has a Role.
- Each Role has a list of Privileges.
- Controller access is annotated based on specific privileges, not just roles.
- Passwords are hashed using BCrypt.

Example Access Control:
Endpoint                  | Required Access
--------------------------|------------------------
/api/airplane (GET)       | USER, ADMIN
/api/airplane (POST)      | ADMIN only
/api/reservation (POST)   | Any authenticated user
/auth/register            | Public

4. Tech Stack

- Backend: Java 17, Spring Boot
- Database: MongoDB (Spring Data Mongo)
- Security: JWT, Spring Security, BCrypt
- Build Tool: Maven
- Containerization: Docker
- Deployment: Compatible with Genezio / cloud

5. Running the Application

Local:
./mvnw clean install
./mvnw spring-boot:run

Docker:
docker build -t callapp .
docker run -p 8080:8080 callapp

6. Core APIs

Endpoint              | Method | Description
-----------------------|--------|-------------------------------
/auth/register         | POST   | Register new user
/auth/login            | POST   | Login and receive JWT
/api/routes            | GET    | List available routes
/api/airplane          | POST   | Add new airplane route
/api/reservation       | POST   | Create reservation

7. Future Enhancements

- Swagger/OpenAPI documentation
- React frontend with route visualizer
- Automatic graph-based pathfinding
- Analytics: most popular routes, user behaviors

8. Author

Mihnea Aniculesei
GitHub: https://github.com/AMihneaa

9. License

MIT License
