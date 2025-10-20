# 🔐 Authentication Service

> Microservicio empresarial de autenticación y autorización con arquitectura reactiva

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-blue.svg)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

Sistema completo de gestión de usuarios, roles y permisos con **Spring Boot WebFlux**, **R2DBC**, **JWT** y **PostgreSQL**. Implementa autenticación segura, control de acceso basado en roles (RBAC) y arquitectura reactiva para alta concurrencia.

---

## 📋 Tabla de Contenidos

- [Características](#-características-principales)
- [Tecnologías](#-stack-tecnológico)
- [Arquitectura](#-arquitectura)
- [Modelo de Datos](#-modelo-de-datos)
- [Endpoints API](#-endpoints-api)
- [Configuración](#-configuración)
- [Instalación](#-instalación)
- [Seguridad](#-seguridad)
- [Documentación](#-documentación)

---

## ✨ Características Principales

### 🔐 Autenticación y Seguridad

- **JWT Authentication** - Tokens de acceso y renovación
- **Spring Security WebFlux** - Seguridad reactiva completa
- **BCrypt Password Encoding** - Encriptación robusta
- **Rate Limiting** - Protección contra ataques DDoS
- **CORS Configurado** - Integración segura con frontend
- **Bloqueo Automático** - Protección contra fuerza bruta

### 👥 Gestión de Usuarios

- **CRUD Completo** - Crear, leer, actualizar, eliminar
- **Estados de Usuario** - ACTIVE, INACTIVE, SUSPENDED
- **Búsquedas Avanzadas** - Por username, documento, email
- **Paginación** - Soporte para grandes volúmenes de datos
- **Validaciones Exhaustivas** - Bean Validation + personalizadas
- **Auditoría Automática** - Registro de cambios y timestamps

### 🎭 Sistema RBAC (Role-Based Access Control)

- **Roles Jerárquicos** - SUPER_ADMIN, ADMIN, USER_MANAGER, VIEWER
- **Permisos Granulares** - Por módulo, acción y recurso
- **Asignación Flexible** - Usuario-Rol con expiración
- **Roles del Sistema** - Roles protegidos no modificables
- **Permisos Efectivos** - Cálculo automático por usuario

### 🛠️ Características Técnicas

- **Arquitectura Reactiva** - WebFlux + R2DBC
- **Base de Datos PostgreSQL** - Esquema robusto con constraints
- **Documentación Swagger** - API interactiva
- **Logging Avanzado** - Rotación automática de logs
- **Monitoreo** - Spring Boot Actuator
- **Configuración Flexible** - Variables de entorno

---

## 🚀 Stack Tecnológico

### Backend Framework

| Tecnología            | Versión | Descripción               |
| --------------------- | ------- | ------------------------- |
| **Java**              | 17 LTS  | Lenguaje de programación  |
| **Spring Boot**       | 3.5.6   | Framework empresarial     |
| **Spring WebFlux**    | 6.x     | Programación reactiva     |
| **Spring Data R2DBC** | 3.x     | Acceso reactivo a BD      |
| **Spring Security**   | 6.x     | Seguridad y autenticación |

### Seguridad

| Tecnología | Versión | Descripción                 |
| ---------- | ------- | --------------------------- |
| **JJWT**   | 0.12.3  | JSON Web Tokens             |
| **BCrypt** | -       | Encriptación de contraseñas |

### Base de Datos

| Tecnología           | Versión | Descripción              |
| -------------------- | ------- | ------------------------ |
| **PostgreSQL**       | 15+     | Base de datos relacional |
| **R2DBC PostgreSQL** | 1.x     | Driver reactivo          |

### Documentación y Validación

| Tecnología             | Versión | Descripción               |
| ---------------------- | ------- | ------------------------- |
| **SpringDoc OpenAPI**  | 2.7.0   | Documentación Swagger     |
| **Jakarta Validation** | 3.x     | Validaciones declarativas |
| **Lombok**             | 1.18.x  | Reducción de boilerplate  |

### Herramientas

| Tecnología          | Versión | Descripción             |
| ------------------- | ------- | ----------------------- |
| **Maven**           | 3.9+    | Gestión de dependencias |
| **SLF4J + Logback** | 2.x     | Sistema de logging      |
| **Jackson**         | 2.x     | Serialización JSON      |

---

## 🏗️ Arquitectura

### Arquitectura en Capas

```
┌─────────────────────────────────────────────────────────────┐
│                    CAPA DE PRESENTACIÓN                      │
│  ┌────────────────────────────────────────────────────┐     │
│  │  Controllers (REST API)                            │     │
│  │  - AuthController                                  │     │
│  │  - UserController                                  │     │
│  │  - PersonController                                │     │
│  │  - RoleController                                  │     │
│  │  - PermissionController                            │     │
│  │  - AssignmentController                            │     │
│  │  - DocumentTypeController                          │     │
│  └────────────────────────────────────────────────────┘     │
│                                                               │
│  ┌────────────────────────────────────────────────────┐     │
│  │  Security Filters                                  │     │
│  │  - JwtAuthenticationFilter                         │     │
│  │  - RateLimitFilter                                 │     │
│  │  - CorsFilter                                      │     │
│  └────────────────────────────────────────────────────┘     │
└───────────────────────────┬───────────────────────────────────┘
                            │ DTOs
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                    CAPA DE LÓGICA DE NEGOCIO                 │
│  ┌────────────────────────────────────────────────────┐     │
│  │  Services (Interfaces + Implementaciones)          │     │
│  │  - AuthService / AuthServiceImpl                   │     │
│  │  - UserService / UserServiceImpl                   │     │
│  │  - PersonService / PersonServiceImpl               │     │
│  │  - RoleService / RoleServiceImpl                   │     │
│  │  - PermissionService / PermissionServiceImpl       │     │
│  │  - AssignmentService / AssignmentServiceImpl       │     │
│  │  - JwtService                                      │     │
│  └────────────────────────────────────────────────────┘     │
└───────────────────────────┬───────────────────────────────────┘
                            │ Entities
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                    CAPA DE PERSISTENCIA                       │
│  ┌────────────────────────────────────────────────────┐     │
│  │  Repositories (Spring Data R2DBC)                  │     │
│  │  - UserRepository                                  │     │
│  │  - PersonRepository                                │     │
│  │  - RoleRepository                                  │     │
│  │  - PermissionRepository                            │     │
│  │  - UserRoleRepository                              │     │
│  │  - RolePermissionRepository                        │     │
│  │  - DocumentTypeRepository                          │     │
│  └────────────────────────────────────────────────────┘     │
└───────────────────────────┬───────────────────────────────────┘
                            │ SQL Queries
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                    BASE DE DATOS                              │
│                    PostgreSQL 15+                             │
└─────────────────────────────────────────────────────────────┘
```

### Estructura del Proyecto

```
src/main/java/edu/pe/vallegrande/AuthenticationService/
├── config/                    # Configuraciones
│   ├── CorsConfig.java
│   ├── SecurityConfig.java
│   ├── SwaggerConfig.java
│   └── ...
├── controller/                # Controladores REST
│   ├── AuthController.java
│   ├── UserController.java
│   └── ...
├── dto/                       # Data Transfer Objects
│   ├── validation/            # Validaciones personalizadas
│   │   ├── MinimumAge.java
│   │   ├── MinimumAgeValidator.java
│   │   └── ValidationUtils.java
│   ├── LoginRequestDto.java
│   ├── UserResponseDto.java
│   └── ...
├── exception/                 # Manejo de excepciones
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   └── ...
├── model/                     # Entidades de dominio
│   ├── User.java
│   ├── Person.java
│   ├── Role.java
│   └── ...
├── repository/                # Acceso a datos
│   ├── UserRepository.java
│   ├── PersonRepository.java
│   └── ...
├── security/                  # Seguridad
│   ├── SecurityConfig.java
│   ├── JwtAuthenticationFilter.java
│   ├── RateLimitFilter.java
│   └── SecurityUtils.java
├── service/                   # Lógica de negocio
│   ├── impl/                  # Implementaciones
│   │   ├── AuthServiceImpl.java
│   │   ├── UserServiceImpl.java
│   │   └── ...
│   ├── AuthService.java
│   ├── UserService.java
│   └── ...
└── AuthenticationServiceApplication.java
```

---

## 📊 Modelo de Datos

### Diagrama Entidad-Relación

```
┌──────────────────┐
│  document_types  │
│  ──────────────  │
│  PK id           │
│     code         │
│     description  │
│     length       │
│     active       │
└────────┬─────────┘
         │
         │ 1:N
         ▼
┌──────────────────┐       ┌──────────────────┐       ┌──────────────────┐
│     persons      │       │      users       │       │   users_roles    │
│  ──────────────  │◄──────│  ──────────────  │──────►│  ──────────────  │
│  PK id           │  1:1  │  PK id           │  N:M  │  PK user_id      │
│  FK document_type│       │  FK person_id    │       │  PK role_id      │
│     doc_number   │       │     username     │       │  FK assigned_by  │
│     first_name   │       │     password_hash│       │     assigned_at  │
│     last_name    │       │     status       │       │     expiration   │
│     birth_date   │       │     last_login   │       │     active       │
│     gender       │       │     login_attempts│      └────────┬─────────┘
│     email        │       │     blocked_until│               │
│     phone        │       │     created_at   │               │ N:1
│     address      │       │     updated_at   │               ▼
└──────────────────┘       └──────────────────┘       ┌──────────────────┐
                                                       │      roles       │
                                                       │  ──────────────  │
                                                       │  PK id           │
                                                       │     name         │
                                                       │     description  │
                                                       │     is_system    │
                                                       │     active       │
                                                       │     created_at   │
                                                       └────────┬─────────┘
                                                                │
                                                                │ N:M
                                                                ▼
                                                       ┌──────────────────┐
                                                       │roles_permissions │
                                                       │  ──────────────  │
                                                       │  PK role_id      │
                                                       │  PK permission_id│
                                                       │     created_at   │
                                                       └────────┬─────────┘
                                                                │
                                                                │ N:1
                                                                ▼
                                                       ┌──────────────────┐
                                                       │   permissions    │
                                                       │  ──────────────  │
                                                       │  PK id           │
                                                       │     module       │
                                                       │     action       │
                                                       │     resource     │
                                                       │     description  │
                                                       │     created_at   │
                                                       └──────────────────┘
```

### Tablas Principales

#### 📋 users

Almacena las credenciales y estado de los usuarios del sistema.

| Campo            | Tipo         | Descripción                         |
| ---------------- | ------------ | ----------------------------------- |
| `id`             | UUID         | Identificador único (PK)            |
| `username`       | VARCHAR(50)  | Nombre de usuario único             |
| `password_hash`  | VARCHAR(500) | Contraseña encriptada (BCrypt)      |
| `person_id`      | UUID         | Referencia a persona (FK)           |
| `status`         | VARCHAR(20)  | Estado: ACTIVE, INACTIVE, SUSPENDED |
| `last_login`     | TIMESTAMP    | Última fecha de inicio de sesión    |
| `login_attempts` | INTEGER      | Contador de intentos fallidos       |
| `blocked_until`  | TIMESTAMP    | Fecha de desbloqueo automático      |
| `created_at`     | TIMESTAMP    | Fecha de creación                   |
| `updated_at`     | TIMESTAMP    | Fecha de última actualización       |

#### 👤 persons

Información personal de los usuarios.

| Campo              | Tipo         | Descripción               |
| ------------------ | ------------ | ------------------------- |
| `id`               | UUID         | Identificador único (PK)  |
| `document_type_id` | INTEGER      | Tipo de documento (FK)    |
| `document_number`  | VARCHAR(20)  | Número de documento único |
| `first_name`       | VARCHAR(100) | Primer nombre             |
| `last_name`        | VARCHAR(100) | Apellidos                 |
| `birth_date`       | DATE         | Fecha de nacimiento       |
| `gender`           | CHAR(1)      | Género: M, F              |
| `personal_email`   | VARCHAR(200) | Email personal            |
| `personal_phone`   | VARCHAR(20)  | Teléfono personal         |
| `address`          | TEXT         | Dirección                 |

#### 🎭 roles

Definición de roles del sistema.

| Campo         | Tipo        | Descripción                            |
| ------------- | ----------- | -------------------------------------- |
| `id`          | UUID        | Identificador único (PK)               |
| `name`        | VARCHAR(50) | Nombre del rol único                   |
| `description` | TEXT        | Descripción del rol                    |
| `is_system`   | BOOLEAN     | Si es rol del sistema (no modificable) |
| `active`      | BOOLEAN     | Si el rol está activo                  |
| `created_at`  | TIMESTAMP   | Fecha de creación                      |

#### 🔑 permissions

Permisos granulares del sistema.

| Campo         | Tipo         | Descripción                   |
| ------------- | ------------ | ----------------------------- |
| `id`          | UUID         | Identificador único (PK)      |
| `module`      | VARCHAR(50)  | Módulo del sistema            |
| `action`      | VARCHAR(50)  | Acción: read, write, delete   |
| `resource`    | VARCHAR(100) | Recurso específico (opcional) |
| `description` | TEXT         | Descripción del permiso       |
| `created_at`  | TIMESTAMP    | Fecha de creación             |

#### 🔗 users_roles

Asignación de roles a usuarios.

| Campo             | Tipo      | Descripción                    |
| ----------------- | --------- | ------------------------------ |
| `user_id`         | UUID      | ID del usuario (PK, FK)        |
| `role_id`         | UUID      | ID del rol (PK, FK)            |
| `assigned_by`     | UUID      | Usuario que asignó el rol      |
| `assigned_at`     | TIMESTAMP | Fecha de asignación            |
| `expiration_date` | DATE      | Fecha de expiración (opcional) |
| `active`          | BOOLEAN   | Si la asignación está activa   |

#### 🔗 roles_permissions

Asignación de permisos a roles.

| Campo           | Tipo      | Descripción             |
| --------------- | --------- | ----------------------- |
| `role_id`       | UUID      | ID del rol (PK, FK)     |
| `permission_id` | UUID      | ID del permiso (PK, FK) |
| `created_at`    | TIMESTAMP | Fecha de asignación     |

#### 📄 document_types

Tipos de documentos de identidad.

| Campo         | Tipo        | Descripción                          |
| ------------- | ----------- | ------------------------------------ |
| `id`          | INTEGER     | Identificador único (PK)             |
| `code`        | VARCHAR(5)  | Código del documento (DNI, CE, PASS) |
| `description` | VARCHAR(50) | Descripción del tipo                 |
| `length`      | INTEGER     | Longitud del documento               |
| `active`      | BOOLEAN     | Si está activo                       |

---

## 🌐 Endpoints API

### 🔐 Autenticación

| Método | Endpoint                | Descripción          | Auth      |
| ------ | ----------------------- | -------------------- | --------- |
| `POST` | `/api/v1/auth/login`    | Iniciar sesión       | Público   |
| `POST` | `/api/v1/auth/logout`   | Cerrar sesión        | Requerido |
| `POST` | `/api/v1/auth/refresh`  | Renovar access token | Público   |
| `POST` | `/api/v1/auth/validate` | Validar token JWT    | Requerido |

### 👤 Usuarios

| Método   | Endpoint                            | Descripción               | Roles                       |
| -------- | ----------------------------------- | ------------------------- | --------------------------- |
| `GET`    | `/api/v1/users`                     | Listar usuarios           | ADMIN, USER_MANAGER, VIEWER |
| `GET`    | `/api/v1/users/{id}`                | Obtener usuario por ID    | ADMIN, USER_MANAGER, VIEWER |
| `GET`    | `/api/v1/users/username/{username}` | Buscar por username       | ADMIN, USER_MANAGER         |
| `GET`    | `/api/v1/users/exists/{username}`   | Verificar existencia      | ADMIN                       |
| `POST`   | `/api/v1/users`                     | Crear usuario             | SUPER_ADMIN, ADMIN          |
| `PUT`    | `/api/v1/users/{id}`                | Actualizar usuario        | SUPER_ADMIN, ADMIN          |
| `DELETE` | `/api/v1/users/{id}`                | Eliminar usuario (lógico) | SUPER_ADMIN                 |
| `PATCH`  | `/api/v1/users/{id}/restore`        | Restaurar usuario         | SUPER_ADMIN                 |
| `PATCH`  | `/api/v1/users/{id}/suspend`        | Suspender usuario         | SUPER_ADMIN, ADMIN          |
| `PATCH`  | `/api/v1/users/{id}/block`          | Bloquear usuario          | SUPER_ADMIN, ADMIN          |
| `PATCH`  | `/api/v1/users/{id}/unblock`        | Desbloquear usuario       | SUPER_ADMIN, ADMIN          |

### 👥 Personas

| Método   | Endpoint                                     | Descripción               | Roles                            |
| -------- | -------------------------------------------- | ------------------------- | -------------------------------- |
| `GET`    | `/api/v1/persons`                            | Listar personas           | ADMIN, USER_MANAGER, VIEWER      |
| `GET`    | `/api/v1/persons/{id}`                       | Obtener persona por ID    | ADMIN, USER_MANAGER, VIEWER      |
| `GET`    | `/api/v1/persons/document/{typeId}/{number}` | Buscar por documento      | ADMIN, USER_MANAGER              |
| `GET`    | `/api/v1/persons/email/{email}`              | Buscar por email          | ADMIN, USER_MANAGER              |
| `GET`    | `/api/v1/persons/search/name/{name}`         | Buscar por nombre         | ADMIN, USER_MANAGER              |
| `GET`    | `/api/v1/persons/active`                     | Listar activas            | ADMIN, USER_MANAGER              |
| `GET`    | `/api/v1/persons/inactive`                   | Listar inactivas          | ADMIN                            |
| `POST`   | `/api/v1/persons`                            | Crear persona             | SUPER_ADMIN, ADMIN, USER_MANAGER |
| `PUT`    | `/api/v1/persons/{id}`                       | Actualizar persona        | SUPER_ADMIN, ADMIN, USER_MANAGER |
| `DELETE` | `/api/v1/persons/{id}`                       | Eliminar persona (lógico) | SUPER_ADMIN, ADMIN               |
| `PATCH`  | `/api/v1/persons/{id}/restore`               | Restaurar persona         | SUPER_ADMIN, ADMIN               |

### 🎭 Roles

| Método   | Endpoint                     | Descripción           | Roles                      |
| -------- | ---------------------------- | --------------------- | -------------------------- |
| `GET`    | `/api/v1/roles`              | Listar roles          | SUPER_ADMIN, ADMIN, VIEWER |
| `GET`    | `/api/v1/roles/{id}`         | Obtener rol por ID    | SUPER_ADMIN, ADMIN, VIEWER |
| `GET`    | `/api/v1/roles/name/{name}`  | Buscar por nombre     | SUPER_ADMIN, ADMIN         |
| `POST`   | `/api/v1/roles`              | Crear rol             | SUPER_ADMIN                |
| `PUT`    | `/api/v1/roles/{id}`         | Actualizar rol        | SUPER_ADMIN                |
| `DELETE` | `/api/v1/roles/{id}`         | Eliminar rol (lógico) | SUPER_ADMIN                |
| `PATCH`  | `/api/v1/roles/{id}/restore` | Restaurar rol         | SUPER_ADMIN                |

### 🔑 Permisos

| Método   | Endpoint                           | Descripción               | Roles                      |
| -------- | ---------------------------------- | ------------------------- | -------------------------- |
| `GET`    | `/api/v1/permissions`              | Listar permisos           | SUPER_ADMIN, ADMIN, VIEWER |
| `GET`    | `/api/v1/permissions/{id}`         | Obtener permiso por ID    | SUPER_ADMIN, ADMIN         |
| `GET`    | `/api/v1/permissions/search`       | Buscar permisos           | SUPER_ADMIN, ADMIN         |
| `POST`   | `/api/v1/permissions`              | Crear permiso             | SUPER_ADMIN                |
| `PUT`    | `/api/v1/permissions/{id}`         | Actualizar permiso        | SUPER_ADMIN                |
| `DELETE` | `/api/v1/permissions/{id}`         | Eliminar permiso (lógico) | SUPER_ADMIN                |
| `PATCH`  | `/api/v1/permissions/{id}/restore` | Restaurar permiso         | SUPER_ADMIN                |

### 🔗 Asignaciones

| Método   | Endpoint                                                    | Descripción           | Roles              |
| -------- | ----------------------------------------------------------- | --------------------- | ------------------ |
| `GET`    | `/api/v1/users/{userId}/roles`                              | Roles de un usuario   | SUPER_ADMIN, ADMIN |
| `POST`   | `/api/v1/users/{userId}/roles/{roleId}`                     | Asignar rol a usuario | SUPER_ADMIN, ADMIN |
| `DELETE` | `/api/v1/users/{userId}/roles/{roleId}`                     | Quitar rol a usuario  | SUPER_ADMIN, ADMIN |
| `GET`    | `/api/v1/roles/{roleId}/users`                              | Usuarios con un rol   | SUPER_ADMIN, ADMIN |
| `GET`    | `/api/v1/roles/{roleId}/permissions`                        | Permisos de un rol    | SUPER_ADMIN, ADMIN |
| `POST`   | `/api/v1/roles/{roleId}/permissions/{permissionId}`         | Asignar permiso a rol | SUPER_ADMIN        |
| `DELETE` | `/api/v1/roles/{roleId}/permissions/{permissionId}`         | Quitar permiso a rol  | SUPER_ADMIN        |
| `PATCH`  | `/api/v1/roles/{roleId}/permissions/{permissionId}/restore` | Restaurar permiso     | SUPER_ADMIN        |
| `GET`    | `/api/v1/users/{userId}/effective-permissions`              | Permisos efectivos    | SUPER_ADMIN, ADMIN |

### 📄 Tipos de Documento

| Método | Endpoint                      | Descripción    | Auth    |
| ------ | ----------------------------- | -------------- | ------- |
| `GET`  | `/api/v1/document-types`      | Listar tipos   | Público |
| `GET`  | `/api/v1/document-types/{id}` | Obtener por ID | Público |

---

## ⚙️ Configuración

### Variables de Entorno

```bash
# Base de Datos
DATABASE_URL=r2dbc:postgresql://host:port/database?sslmode=require&connectTimeout=30000
DB_USERNAME=your_username
DB_PASSWORD=your_password

# JWT Security
JWT_SECRET=your-256-bit-secret-key-minimum
JWT_EXPIRATION=3600000          # 1 hora en milisegundos
JWT_REFRESH_EXPIRATION=86400000 # 24 horas en milisegundos

# Servidor
PORT=5002
SPRING_PROFILES_ACTIVE=production
```

### application.yml

```yaml
server:
  port: ${PORT:5002}

spring:
  application:
    name: AuthenticationService
  
  r2dbc:
    url: ${DATABASE_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    pool:
      initial-size: 1
      max-size: 10
      max-idle-time: 5m
      max-acquire-time: 30s
      validation-query: SELECT 1

jwt:
  secret: ${JWT_SECRET}
  expiration: ${JWT_EXPIRATION:3600000}
  refresh-expiration: ${JWT_REFRESH_EXPIRATION:86400000}

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics

logging:
  level:
    root: INFO
    edu.pe.vallegrande.AuthenticationService: DEBUG
  file:
    name: logs/authentication-service.log
    max-size: 10MB
    max-history: 30
```