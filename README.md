# 📝 My Notes API

Backend REST API untuk aplikasi notes management yang dibangun dengan **Kotlin**, **Spring Boot 3.x**, dan **MongoDB** menggunakan **Clean Architecture**.

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-purple.svg)](https://kotlinlang.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green.svg)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-7.0-green.svg)](https://www.mongodb.com/)

## ✨ Fitur Utama

- 🔐 **JWT Authentication** - Access token & refresh token
- 📝 **CRUD Notes** - Create, Read, Update, Delete dengan soft delete
- 🔍 **Advanced Search** - Full-text search di title dan content
- 🏷️ **Tag Management** - Filter notes berdasarkan tags
- 📄 **Pagination & Sorting** - Efficient data retrieval
- 🗂️ **Archive System** - Archive/unarchive notes
- 👤 **User Management** - Register & login dengan role-based access
- 🏗️ **Clean Architecture** - Separation of concerns yang jelas
- 📚 **API Documentation** - Swagger/OpenAPI integration

---

## 📋 Table of Contents

- [Prasyarat](#-prasyarat)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Quick Start](#-quick-start)
- [Environment Variables](#-environment-variables)
- [API Endpoints](#-api-endpoints)

---

## 🔧 Prasyarat

Pastikan tools berikut sudah terinstall di sistem Anda:

| Tool | Version | Purpose |
|------|---------|---------|
| **JDK** | 17+ | Runtime Java/Kotlin |
| **MongoDB** | 7.0+ | Database server |
| **Gradle** | 8.0+ | Build tool (wrapper included) |

## 🛠️ Tech Stack

### Backend Core
- **Kotlin** 1.9+ - Modern JVM language
- **Spring Boot** 3.x - Application framework
- **Spring Security** - Authentication & authorization
- **Spring Data MongoDB** - Database access layer

### Database
- **MongoDB** 7.0 - NoSQL document database
- **MongoDB Reactive** - Reactive streams support

### Security & JWT
- **JJWT** 0.12.6 - JWT token generation & validation
- **BCrypt** - Password hashing

### API Documentation
- **SpringDoc OpenAPI** 2.6.0 - Swagger UI integration

---

## 🏗️ Project Structure

```
my-notes-api/
├── settings.gradle.kts
├── build.gradle.kts
├── README.md
└── src/
    └── main/
        ├── kotlin/com/muhdila/mynotesapi/
        │   ├── MyNotesApiApplication.kt
        │   │
        │   ├── core/                          # 🎯 DOMAIN LAYER
        │   │   ├── domain/
        │   │   │   ├── User.kt               # Domain entity
        │   │   │   ├── Note.kt               # Domain entity
        │   │   │   └── error/
        │   │   │       └── DomainExceptions.kt
        │   │   └── usecase/
        │   │       ├── auth/
        │   │       │   ├── RegisterUserUseCase.kt
        │   │       │   └── AuthenticateUserUseCase.kt
        │   │       └── notes/
        │   │           └── NoteUseCases.kt
        │   │
        │   ├── application/                   # 🔄 APPLICATION LAYER
        │   │   ├── port/
        │   │   │   └── outbound/
        │   │   │       ├── NoteRepositoryPort.kt
        │   │   │       ├── UserRepositoryPort.kt
        │   │   │       ├── PasswordHasherPort.kt
        │   │   │       └── TokenProviderPort.kt
        │   │   └── service/
        │   │       ├── AuthService.kt        # Business orchestration
        │   │       └── NoteService.kt
        │   │
        │   ├── infrastructure/                # 🔌 INFRASTRUCTURE LAYER
        │   │   ├── persistence/mongo/
        │   │   │   ├── document/
        │   │   │   │   ├── NoteDocument.kt   # MongoDB document
        │   │   │   │   └── UserDocument.kt
        │   │   │   ├── SpringDataRepositories.kt
        │   │   │   ├── NoteQueryRepository.kt
        │   │   │   ├── NoteRepositoryMongo.kt
        │   │   │   └── UserRepositoryMongo.kt
        │   │   ├── security/
        │   │   │   ├── JwtTokenProvider.kt   # JWT implementation
        │   │   │   ├── JwtAuthenticationFilter.kt
        │   │   │   ├── SecurityConfig.kt
        │   │   │   └── UserDetailsServiceImpl.kt
        │   │   ├── config/
        │   │   │   ├── MongoConfig.kt        # MongoDB indexes
        │   │   │   ├── SwaggerConfig.kt
        │   │   │   ├── CorsConfig.kt
        │   │   │   └── DataSeeder.kt         # Initial data
        │   │   └── mapper/
        │   │       ├── NoteMapper.kt         # Domain ↔ Document
        │   │       └── UserMapper.kt
        │   │
        │   └── interfaces/                    # 🌐 PRESENTATION LAYER
        │       ├── api/
        │       │   ├── AuthController.kt     # REST endpoints
        │       │   ├── NoteController.kt
        │       │   └── dto/
        │       │       ├── ApiResponse.kt
        │       │       ├── auth/
        │       │       │   └── AuthDtos.kt   # Request/Response DTOs
        │       │       └── notes/
        │       │           └── NoteDtos.kt
        │       └── advice/
        │           └── GlobalExceptionHandler.kt
        │
        └── resources/
            └── application.properties
```

### 🎯 Layer Responsibilities

#### 1. **Core Layer** (Domain + Use Cases)
**Domain:**
- ✅ Business entities (User, Note)
- ✅ Domain rules dan validations
- ✅ Domain exceptions

**Use Cases:**
- ✅ Business use case scenarios
- ✅ Koordinasi antara entities
- ✅ Aturan bisnis kompleks

#### 2. **Application Layer**
**Ports:**
- ✅ Interface untuk repository (outbound ports)
- ✅ Abstraction dari technical details

**Services:**
- ✅ Orchestrate use cases
- ✅ Transaction management
- ✅ Koordinasi antar ports

#### 3. **Infrastructure Layer**
**Persistence:**
- ✅ MongoDB documents & repositories
- ✅ Query implementations
- ✅ Database indexes configuration

**Security:**
- ✅ JWT token provider implementation
- ✅ Authentication filters
- ✅ Security configuration

**Config:**
- ✅ Application configurations
- ✅ Bean definitions
- ✅ Data seeding

**Mapper:**
- ✅ Convert domain entities ↔ database documents

#### 4. **Interfaces Layer**
**API:**
- ✅ REST controllers
- ✅ Request/Response DTOs
- ✅ Input validation

**Advice:**
- ✅ Global exception handling
- ✅ Error responses formatting

### 🔄 Dependency Rule

```
    Core (Domain + Use Cases)
           ↑
           |
      Application (Ports + Services)
           ↑
           |
    +------+------+
    |             |
Infrastructure  Interfaces
```

**Prinsip:** Dependencies selalu mengarah ke dalam. Layer dalam tidak tahu tentang layer luar.

---

## 🚀 Quick Start

### 1. Clone Repository

```bash
git clone https://github.com/muhdila/my-notes-api.git
cd my-notes-api
```

### 2. Setup MongoDB

Pastikan MongoDB sudah running di `localhost:27017`

**Verifikasi MongoDB:**
```bash
# Check MongoDB status
mongosh --eval "db.runCommand({ connectionStatus: 1 })"
```

**Atau start MongoDB service:**
```bash
# macOS
brew services start mongodb-community@7.0

# Ubuntu/Debian
sudo systemctl start mongod

# Windows
net start MongoDB
```

### 3. Configure Environment

Buat file `application.properties` di `src/main/resources/`:

```properties
# MongoDB Configuration
spring.data.mongodb.uri=mongodb://localhost:27017/mynotes
spring.data.mongodb.auto-index-creation=true

# JWT Configuration
jwt.secret=your-super-secret-key-minimum-256-bits-for-hs256-algorithm-change-in-production
jwt.access-expiration-millis=3600000
jwt.refresh-expiration-millis=604800000

# Server Configuration
server.port=8080

# CORS Configuration
cors.allowed-origins=http://localhost:3000,http://localhost:5173

# Logging
logging.level.com.muhdila.mynotesapi=DEBUG
logging.level.org.springframework.security=DEBUG
```

### 4. Build & Run

```bash
# Build project
./gradlew build

# Run application
./gradlew bootRun

# Or run with custom profile
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### 5. Verify API is Running

```bash
# Health check (if actuator enabled)
curl http://localhost:8080/actuator/health

# Or check Swagger UI
open http://localhost:8080/swagger-ui.html
```

**Expected output:**
```
Application started on port 8080
MongoDB connected successfully
Indexes created for collections
```

---

## 🔐 Environment Variables

### Required Configuration

| Variable | Description | Example | Default |
|----------|-------------|---------|---------|
| `MONGO_URI` | MongoDB connection string | `mongodb://localhost:27017/mynotes` | - |
| `JWT_SECRET` | Secret key for JWT (min 256 bits) | `your-secret-key-here` | - |
| `JWT_ACCESS_EXPIRATION` | Access token expiry (ms) | `3600000` | 1 hour |
| `JWT_REFRESH_EXPIRATION` | Refresh token expiry (ms) | `604800000` | 7 days |

### Optional Configuration

| Variable | Description | Default |
|----------|-------------|---------|
| `SERVER_PORT` | Server port | `8080` |
| `CORS_ALLOWED_ORIGINS` | Allowed CORS origins | `*` |
| `LOGGING_LEVEL` | Log level (DEBUG/INFO/WARN/ERROR) | `INFO` |

### Setup Methods

**Option 1: application.properties (Recommended)**
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/mynotes
jwt.secret=your-super-secret-key
jwt.access-expiration-millis=3600000
jwt.refresh-expiration-millis=604800000
```

**Option 2: Environment Variables**
```bash
export MONGO_URI=mongodb://localhost:27017/mynotes
export JWT_SECRET=your-super-secret-key
export JWT_ACCESS_EXPIRATION=3600000
export JWT_REFRESH_EXPIRATION=604800000

./gradlew bootRun
```

**Option 3: Command Line Arguments**
```bash
./gradlew bootRun --args='
  --spring.data.mongodb.uri=mongodb://localhost:27017/mynotes
  --jwt.secret=your-super-secret-key
'
```

### 🔒 Security Best Practices

- ⚠️ **NEVER** commit JWT secret to version control
- ⚠️ Use strong, random secret keys (min 256 bits for HS256)
- ⚠️ Rotate secrets regularly in production
- ⚠️ Use different secrets for dev/staging/production

**Generate secure JWT secret:**
```bash
# Generate 256-bit base64 encoded key
openssl rand -base64 32

# Or use online generator
# https://generate-secret.vercel.app/32
```

---

## 📡 API Endpoints

### Authentication

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `POST` | `/api/v1/auth/register` | Register new user | ❌ |
| `POST` | `/api/v1/auth/login` | Login & get tokens | ❌ |
| `POST` | `/api/v1/auth/refresh` | Refresh access token | ❌ |

### Notes Management

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `POST` | `/api/v1/notes` | Create new note | ✅ Bearer Token |
| `GET` | `/api/v1/notes` | List notes (with filters) | ✅ Bearer Token |
| `GET` | `/api/v1/notes/{id}` | Get note by ID | ✅ Bearer Token |
| `PATCH` | `/api/v1/notes/{id}` | Partial update note | ✅ Bearer Token |
| `PUT` | `/api/v1/notes/{id}` | Full update note | ✅ Bearer Token |
| `DELETE` | `/api/v1/notes/{id}` | Soft delete note | ✅ Bearer Token |

### 📚 Full API Documentation

**Swagger UI (Interactive):**
```
http://localhost:8080/swagger-ui.html
```

---

## 🤝 Contributing

Contributions are welcome!

**How to contribute:**
1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

**Code Standards:**
- Follow Kotlin coding conventions
- Write meaningful commit messages
- Add tests for new features
- Update documentation

---

## 👨‍💻 Author

**Muhammad Dila**
- GitHub: [@muhdila](https://github.com/muhdila)
- Email: muhammaddila.all@gmail.com
