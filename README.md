# Smart Home System

## Overview

Smart Home System is an IoT-based backend project designed to manage and control 
smart home devices efficiently. This project focuses on backend logic, device 
communication, automation processing, and data management for IoT-based home automation.

This project provides the server-side foundation for controlling devices such as 
lights, fans, and sensors. It handles authentication, device status management, 
automation rules, and communication between hardware devices and frontend applications.

---

## Features

- Device status management
- Smart home device control API
- User authentication and access control
- Temperature, humidity and light data processing
- Automation rule processing
- Real-time backend communication support
- Database management for device logs and history

---

## Technologies Used

### Backend

- Java / Spring Boot
- JWT Authentication
- RESTful API
- PostgreSQL
- Spring WebSocket (STOMP) for real-time communication
- MQTT Protocol for IoT device communication
- Adafruit IO as MQTT broker / intermediate server
- Event-Driven Architecture

### Hardware Integration

- Yolo:Bit microcontroller board
- Temperature & Humidity Sensor
- Light Sensor
- Relay Modules
- Smart Device Control (lights, fan)

---

## Main Functional Modules

### 1. Authentication & Access Control

- User login system
- Secure access for authorized users only

### 2. Environmental Monitoring

- Temperature and light monitoring
- Humidity detection
- Automatic fan or lighting system activation

### 3. Device Automation

- Scheduled appliance control
- Remote ON/OFF operations
- Real-time monitoring and notifications

---

## Project Structure

```text
.
├── Dockerfile
├── docker-compose.yml
├── README.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── websocket-guide.md
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── group26
    │   │           └── smart_home_system
    │   │               ├── SmartHomeSystemApplication.java
    │   │               ├── advice/            # Global exception handling
    │   │               ├── config/            # Application, MQTT, WebSocket configs
    │   │               ├── controller/        # REST API controllers
    │   │               ├── dto/               # Request / Response DTOs
    │   │               ├── entity/            # JPA entities
    │   │               ├── enums/             # Enum definitions
    │   │               ├── event/             # Event-driven processing
    │   │               ├── exception/         # Custom exceptions
    │   │               ├── mapper/            # Entity - DTO mapping
    │   │               ├── mqtt/              # MQTT subscriber / publisher, Message handler
    │   │               ├── repository/        # Spring Data JPA repositories
    │   │               ├── security/          # Security configs, JWT authentication & authorization
    │   │               ├── service/           # Business logic
    │   │               ├── specification/     # Dynamic query specifications
    │   │               ├── utils/             # Utility classes
    │   │               └── websocket/         # STOMP / WebSocket handlers
    │   └── resources
    │       ├── application.yml
    │       ├── application-dev.yml
    │       ├── application-prod.yml
    │       ├── application.yml.example
    │       ├── messages.properties
    │       │
    │       └── db
    │           └── migration
    │               └── V1__smart_home_system.sql
    └── test
```

---

## Setup and Usage

### Prerequisites

Before running the project, make sure you have installed:

- **Java 21+** (required to run the Spring Boot application)
- **Maven 3.9+** (used for dependency management and building the project)
- **PostgreSQL** (used as the main database for the project)
- **Adafruit IO Account** (required for MQTT broker access to send and receive IoT device data)
- **Docker Desktop** (recommended for easier setup on Windows/macOS)
- **Git** (for cloning the repository)

### Clone the repository

```bash
git clone https://github.com/nhtc2005/smart-home-system.git
```

### Run with Docker (Recommended)

1. **Configure environment variables**

Create a `.env` file in the root directory:

```text
# SERVER
SERVER_PORT=8080
SPRING_PROFILES_ACTIVE=dev

# DATABASE
# Used by Spring Boot to connect to PostgreSQL
DB_URL=jdbc:postgresql://localhost:5432/smart_home_system

# Used by Docker PostgreSQL container to create the database
DB_NAME=smart_home_system

DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password

# JWT
JWT_SECRET=your_jwt_secret_here
JWT_EXPIRATION=3600000

# MQTT
MQTT_BROKER_URL=tcp://io.adafruit.com:1883
MQTT_USERNAME=your_mqtt_username
MQTT_KEY=your_mqtt_key
MQTT_CLIENT_ID=spring-backend-client-dev
MQTT_TOPICS=${MQTT_USERNAME}/feeds/+

# LOGGING
LOG_PATH=logs
LOG_FILE=smart-home-system.log
LOG_LEVEL=DEBUG
```

2. **Start services with Docker**

```bash
docker-compose up -d
```

This will automatically start:

- Start PostgreSQL database
- Create the database on first startup
- Start other dependent services

> **Note:** On the first run, Docker will create the database volume and initialize the database automatically.

3. **Access the application**

Application:

```text
http://localhost:8080
```

Swagger UI:

```text
http://localhost:8080/api/swagger-ui/index.html
```

### Run without Docker

1. **Create database manually**

```sql
CREATE DATABASE smart_home_system;
```

2. **Configure `application.yml`**

Create your local configuration file from the example and update the necessary values:

```bash
cp src/main/resources/application.yml.example src/main/resources/application.yml
```

3. Run the application

```bash
./mvnw spring-boot:run
```

4. **Access the application**

Application:

```text
http://localhost:8080
```

Swagger UI:

```text
http://localhost:8080/api/swagger-ui/index.html
```

> **Note:** When running without Docker, you need to manually create the database before starting the application.

---

## WebSocket Testing

You can test WebSocket (STOMP) using:

- Postman
- WebSocket clients
- Frontend dashboard

For detailed guide, see [WebSocket Guide](websocket-guide.md)

---

## MQTT Device Flow

```text
Devices → MQTT → Adafruit IO → Spring Boot Backend → PostgreSQL  
                                       ↓  
                               WebSocket (STOMP)  
                                       ↓  
                               Frontend Dashboard
```
