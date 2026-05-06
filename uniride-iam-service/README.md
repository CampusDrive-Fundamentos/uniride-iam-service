# UniRide - IAM Microservice (Identity & Access Management)

Este microservicio es el núcleo de seguridad y gestión de identidades de la plataforma **UniRide**, diseñado para garantizar un ecosistema universitario 100% seguro y confiable.

## 🏛️ Arquitectura

El servicio sigue estrictamente la **Arquitectura Hexagonal (Ports & Adapters)**, asegurando que el dominio de negocio esté desacoplado de las tecnologías externas (bases de datos, frameworks, APIs).

- **Domain Layer**: Contiene los modelos (`User`, `Student`, `Driver`), repositorios (interfaces) y la lógica de negocio central. Implementa el patrón **Singleton** para la validación de seguridad.
- **Application Layer**: Servicios que orquestan los casos de uso (Registro, Login, Validación de Token) y DTOs.
- **Infrastructure Layer**: Implementaciones técnicas como JPA repositories, adaptadores de persistencia, configuración de Spring Security y JWT.
- **Presentation Layer**: Controladores REST y manejo centralizado de excepciones.

## 🚀 Drivers Arquitectónicos

1. **Seguridad (Crítico)**: 
   - Validación obligatoria de correos institucionales (`.edu`, `.edu.pe`) para estudiantes.
   - Validación de identidad académica mediante foto de TIU (al ser un proyecto universitario, solo se encarga de verificar que este campo este presente y sea válido, mas no realiza validación facial).
   - Filtro estricto de conductores mediante consulta de Antecedentes Penales (se encarga de verificar que este campo este presente y sea válido, mas no realiza validación de que no hayan antecedentes penales por simplicidad). 
   - Autenticación Stateless basada en **JWT (JSON Web Tokens)**.
2. **Rendimiento**: Optimizado para respuestas rápidas y procesamiento ligero de identidades.
3. **Disponibilidad**: Diseñado para ser escalable y desplegado en contenedores (Docker).

## 🛠️ Tecnologías

- **Java 21**
- **Spring Boot 3.x**
- **Spring Security** (JWT)
- **Spring Data JPA**
- **PostgreSQL**
- **Docker & Docker Compose**
- **Lombok**
- **SpringDoc OpenAPI (Swagger)**

## 📋 Requisitos de Registro

### Estudiantes
- **Correo Institucional**: Debe terminar en `.edu` o `.edu.pe`.
- **TIU**: Foto obligatoria validada por el sistema.
- **Información Personal**: Nombres, apellidos, universidad y teléfono.

### Conductores
- **Validación CUL**: Certificado de Antecedentes Penales obligatorio.
- **Documentación**: DNI y Licencia de conducir.
- **Filtro de Seguridad**: Procesado a través de la clase Singleton `SecurityValidator`.

## ⚙️ Configuración y Ejecución

### 1. Levantar la Base de Datos (Docker)
El proyecto incluye un `docker-compose.yml` preconfigurado con PostgreSQL en el puerto `5433`.
```bash
docker-compose up -d
```

### 2. Ejecutar la Aplicación
Ejecutar el servicio ejecutando el `UnirideIamServiceApplication.java` o usando mvnw
```bash
./mvnw spring-boot:run
```

### 3. Documentación API (Swagger)
Una vez iniciada la aplicación, puedes acceder a la interfaz interactiva para probar los endpoints:
[http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

## 🔐 Endpoints Principales

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `POST` | `/api/v1/auth/signup/student` | Registro de nuevos estudiantes (.edu) |
| `POST` | `/api/v1/auth/signup/driver` | Registro de conductores (Validación CUL) |
| `POST` | `/api/v1/auth/signin` | Autenticación y generación de JWT |
| `GET` | `/api/v1/users/me` | Obtener perfil del usuario autenticado |

---
*Desarrollado para el proyecto UniRide - CampusDrive.*
