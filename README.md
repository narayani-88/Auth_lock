# 🔐 Auth_lock — JWT Authentication with Spring Security

A production-style authentication backend built with **Spring Boot 3** and **Spring Security**.  
This project demonstrates how real-world stateless auth works — from user registration to secured endpoints using JWT tokens.

---

## ✨ Features

- ✅ User Registration & Login
- ✅ JWT Token Generation & Validation
- ✅ Stateless Authentication (no sessions)
- ✅ Spring Security filter chain with custom `JwtAuthFilter`
- ✅ Role-Based Access Control (RBAC)
- ✅ Password encoding with BCrypt
- ✅ Clean layered architecture — `config / controller / entity / repository / security`
- ✅ PostgreSQL / MySQL support via Spring Data JPA

---

## 🧱 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2.5 |
| Security | Spring Security + JJWT 0.11.5 |
| Persistence | Spring Data JPA + Hibernate |
| Database | PostgreSQL (MySQL also supported) |
| Utilities | Lombok |
| Build Tool | Maven |

---

## 📁 Project Structure

```
src/main/java/com/auth/demo/
├── config/          # SecurityConfig, CORS, Bean definitions
├── controller/      # AuthController — /register, /login endpoints
├── entity/          # User entity with roles
├── repository/      # UserRepository (JPA)
├── security/        # JwtService, JwtAuthFilter, UserDetailsService impl
└── AuthApplication.java
```

---

## 🔄 Auth Flow

```
Client                        Server
  │                              │
  │──── POST /api/auth/register ─▶│  Save user (BCrypt password)
  │◀─── 200 OK ──────────────────│
  │                              │
  │──── POST /api/auth/login ────▶│  Validate credentials
  │◀─── { "token": "eyJ..." } ───│  Return signed JWT
  │                              │
  │──── GET /api/protected ──────▶│  Request with Bearer token
  │     Authorization: Bearer    │  JwtAuthFilter validates token
  │◀─── 200 OK (protected data) ─│  SecurityContext set → access granted
```

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- PostgreSQL (or MySQL)

### 1. Clone the repository

```bash
git clone https://github.com/narayani-88/Auth_lock.git
cd Auth_lock
```

### 2. Configure the database

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/authdb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

# JWT Secret (use a strong base64-encoded key in production)
jwt.secret=your_jwt_secret_key
jwt.expiration=86400000
```

> **MySQL users:** swap the datasource URL to `jdbc:mysql://localhost:3306/authdb` — the MySQL connector is already included in `pom.xml`.

### 3. Run the application

```bash
./mvnw spring-boot:run
```

App starts at `http://localhost:8080`

---

## 📬 API Endpoints

### Auth (Public)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/auth/register` | Register a new user |
| `POST` | `/api/auth/login` | Login and receive JWT token |

### Protected (Requires Bearer Token)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/user/profile` | Get authenticated user info |

### Example — Register

```json
POST /api/auth/register
{
  "username": "narayani",
  "email": "narayani@example.com",
  "password": "securepassword"
}
```

### Example — Login

```json
POST /api/auth/login
{
  "email": "narayani@example.com",
  "password": "securepassword"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Example — Accessing a Protected Endpoint

```
GET /api/user/profile
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

---

## 🔑 How JWT Works Here

1. On login, the server generates a **signed JWT** containing the user's identity and expiry
2. The client stores this token and sends it in the `Authorization: Bearer <token>` header on every request
3. `JwtAuthFilter` intercepts each request, validates the token signature and expiry, and sets the `SecurityContext`
4. Spring Security grants or denies access based on the authenticated context

---

## 🛡️ Security Design Decisions

- Passwords are never stored in plaintext — **BCrypt** hashing is applied before persistence
- JWT is **stateless** — no server-side session storage needed, scales horizontally
- Token expiry is configurable — default 24 hours
- The Security filter chain explicitly permits `/api/auth/**` and secures all other routes

---

## 📌 What I Learned / Why I Built This

Most tutorials show JWT in 50 lines with no real structure. This project implements auth the way it should look in a production codebase — with proper separation between security filters, service logic, and API controllers. It's the auth foundation I use as a base when starting new Spring Boot projects.

---

## 👤 Author

**Narayani Pandey** — Java Backend Developer  
[LinkedIn](https://www.linkedin.com/in/narayani-pandey/) · [GitHub](https://github.com/narayani-88)
