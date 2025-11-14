# 📝 My Notes API

Backend REST API untuk aplikasi notes management yang dibangun dengan **Kotlin**, **Spring Boot 3.x**, dan **MongoDB** menggunakan **Clean Architecture**.

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-purple.svg)](https://kotlinlang.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green.svg)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-7.0-green.svg)](https://www.mongodb.com/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com/)

## ✨ Fitur Utama

- 🔐 JWT Authentication (Access & Refresh Token)
- 📝 CRUD Notes dengan Soft Delete
- 🔍 Full-text Search & Tag Filtering
- 📄 Pagination & Sorting
- 🗂️ Archive System
- 🏗️ Clean Architecture
- 📚 Swagger/OpenAPI Documentation
- 🐳 Docker Ready

---

## 🚀 Quick Start

### Option A: Docker (Recommended)

```bash
# Clone & setup
git clone https://github.com/muhdila/my-notes-api.git
cd my-notes-api

# Create .env file
cat > .env << EOF
PORT=8080
MONGO_URI=mongodb://mongo:27017/mynotes
CORS_ALLOWED_ORIGINS=http://localhost:3000,http://localhost:5173
JWT_SECRET=$(openssl rand -base64 32)
JWT_ACCESS_EXPIRATION=3600000
JWT_REFRESH_EXPIRATION=604800000
EOF

# Build & run
./gradlew clean build
docker-compose up -d
```

**Access:**
- API: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html

### Option B: Local Development

**Prerequisites:** JDK 17+, MongoDB 7.0+, Gradle 8.0+

```bash
# Start MongoDB
mongosh --eval "db.runCommand({ connectionStatus: 1 })"

# Configure application.properties
spring.data.mongodb.uri=mongodb://localhost:27017/mynotes
jwt.secret=your-secret-key-here
jwt.access-expiration-millis=3600000
jwt.refresh-expiration-millis=604800000

# Run
./gradlew bootRun
```

---

## 🛠️ Tech Stack

- **Kotlin** 1.9+ & **Spring Boot** 3.x
- **MongoDB** 7.0 (Reactive)
- **Spring Security** & **JJWT** 0.12.6
- **SpringDoc OpenAPI** 2.6.0
- **Docker** & **Docker Compose**

---

## 🗂️ Project Structure

```
my-notes-api/
├── Dockerfile
├── docker-compose.yml
├── .env (create this)
└── src/main/kotlin/com/muhdila/mynotesapi/
    ├── core/                      # Domain Layer
    │   ├── domain/                # Entities (User, Note)
    │   └── usecase/               # Business logic
    ├── application/               # Application Layer
    │   ├── port/outbound/         # Repository interfaces
    │   └── service/               # Orchestration
    ├── infrastructure/            # Infrastructure Layer
    │   ├── persistence/mongo/     # MongoDB implementation
    │   ├── security/              # JWT & Security config
    │   └── config/                # App configuration
    └── interfaces/                # Presentation Layer
        ├── api/                   # REST Controllers
        └── advice/                # Exception handling
```

**Clean Architecture Layers:**
```
Core (Domain) → Application → Infrastructure/Interfaces
```

---

## 🐳 Docker

### Services

- **mynotes-mongo** (MongoDB 6) - Port 27017, Volume: `mongo_data`
- **mynotes-api** (Spring Boot) - Port ${PORT}:8080

### Commands

```bash
# Start/Stop
docker-compose up -d
docker-compose down

# Logs
docker-compose logs -f api

# Rebuild
./gradlew clean build
docker-compose up -d --build

# Backup MongoDB
docker-compose exec mongo mongodump --out /data/backup

# Clean up (⚠️ deletes data)
docker-compose down -v
```

### Troubleshooting

| Problem | Solution |
|---------|----------|
| API can't connect to MongoDB | `docker-compose logs mongo` |
| Port already in use | Change `PORT` in `.env` |
| Changes not reflected | `./gradlew clean build && docker-compose up -d --build` |

---

## 📝 Environment Variables

Create `.env` file (Docker) or configure `application.properties` (Local):

```env
# Required
PORT=8080
MONGO_URI=mongodb://mongo:27017/mynotes      # Docker
# MONGO_URI=mongodb://localhost:27017/mynotes # Local
JWT_SECRET=your-256-bit-secret-key-here
JWT_ACCESS_EXPIRATION=3600000                 # 1 hour
JWT_REFRESH_EXPIRATION=604800000              # 7 days

# Optional
CORS_ALLOWED_ORIGINS=http://localhost:3000,http://localhost:5173
```

**⚠️ Security:**
- Add `.env` to `.gitignore`
- Generate secure secret: `openssl rand -base64 32`
- Use different secrets for dev/production

---

## 📡 API Endpoints

### Authentication
- `POST /api/v1/auth/register` - Register user
- `POST /api/v1/auth/login` - Login & get tokens
- `POST /api/v1/auth/refresh` - Refresh access token

### Notes (Requires Bearer Token)
- `POST /api/v1/notes` - Create note
- `GET /api/v1/notes` - List notes (with filters)
- `GET /api/v1/notes/{id}` - Get note by ID
- `PATCH /api/v1/notes/{id}` - Update note
- `DELETE /api/v1/notes/{id}` - Delete note

**Full Documentation:** http://localhost:8080/swagger-ui.html

---

## 🤝 Contributing

1. Fork repository
2. Create feature branch (`git checkout -b feature/name`)
3. Commit changes (`git commit -m 'Add feature'`)
4. Push branch (`git push origin feature/name`)
5. Open Pull Request

---

## 👨‍💻 Author

**Muhammad Dila**
- GitHub: [@muhdila](https://github.com/muhdila)
- Email: muhammaddila.all@gmail.com

---

Made with ❤️ using Kotlin, Spring Boot, and MongoDB