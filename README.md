# Relatos de Papel - Backend Microservices

Backend de la aplicación web "Relatos de Papel" desarrollado con Java y Spring Boot, siguiendo una arquitectura de microservicios.

## Arquitectura

```
┌─────────────────┐
│    Frontend     │
│   (React SPA)   │
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│  Cloud Gateway  │  ← Puerto 8080 (Punto de entrada único)
│   (Spring)      │
└────────┬────────┘
         │
    ┌────┴────┐
    ▼         ▼
┌───────┐  ┌────────┐
│Catalog│  │ Orders │
│ :8081 │  │ :8082  │
└───┬───┘  └───┬────┘
    │          │
    ▼          ▼
┌───────┐  ┌────────┐
│MySQL  │  │ MySQL  │
│catalog│  │ orders │
└───────┘  └────────┘
         
         ▲
         │
┌─────────────────┐
│  Eureka Server  │  ← Puerto 8761 (Service Discovery)
└─────────────────┘
```

## Componentes

| Componente | Puerto | Descripción |
|------------|--------|-------------|
| Eureka Server | 8761 | Servidor de descubrimiento de servicios |
| Cloud Gateway | 8080 | API Gateway / Proxy inverso |
| Catalogue | 8081 | Microservicio de catálogo de libros |
| Orders | 8082 | Microservicio de órdenes de compra |

## Requisitos Previos

- Java 17+
- Maven 3.8+
- MySQL 8.0+

## Configuración de Base de Datos

### 1. Crear las bases de datos

```sql
-- Ejecutar en MySQL
CREATE SCHEMA catalogue_db;
CREATE SCHEMA orders_db;
```

### 2. Ejecutar los scripts DDL

- `src/main/java/com/unir/relatos/catalogue/db/esquema.sql` → Para catalogue_db
- `orders/src/main/java/com/unir/relatos/orders/db/esquema.sql` → Para orders_db

### 3. Cargar datos de ejemplo

- `src/main/java/com/unir/relatos/catalogue/db/data.sql` → 120+ libros
- `orders/src/main/java/com/unir/relatos/orders/db/data.sql` → Órdenes de ejemplo

## Orden de Arranque

**IMPORTANTE**: Los servicios deben arrancarse en este orden:

1. **Eureka Server** (primero, esperar a que esté disponible)
2. **Catalogue Microservice**
3. **Orders Microservice**
4. **Cloud Gateway** (último)

## Comandos de Arranque

### Desde la raíz del proyecto:

```bash
# 1. Eureka Server
cd eureka-server
mvn spring-boot:run

# 2. Catalogue (en otra terminal)
mvn spring-boot:run

# 3. Orders (en otra terminal)
cd orders
mvn spring-boot:run

# 4. Gateway (en otra terminal)
cd gateway
mvn spring-boot:run
```

## Verificación

### Dashboard de Eureka
- URL: http://localhost:8761
- Debe mostrar: CATALOGUE, ORDERS, GATEWAY registrados

### Rutas del Gateway
- URL: http://localhost:8080/actuator/gateway/routes
- Muestra todas las rutas configuradas

## API REST

### Catalogue Microservice (vía Gateway :8080)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/books` | Listar todos los libros |
| GET | `/api/v1/books?title=X&author=Y&genre=Z` | Búsqueda combinada |
| GET | `/api/v1/books/{id}` | Obtener libro por ID |
| POST | `/api/v1/books` | Crear libro |
| PUT | `/api/v1/books/{id}` | Actualizar libro completo |
| PATCH | `/api/v1/books/{id}` | Actualizar libro parcial |
| DELETE | `/api/v1/books/{id}` | Eliminar libro |

**Parámetros de búsqueda disponibles:**
- `title` - Búsqueda por título (LIKE)
- `author` - Búsqueda por autor (LIKE)
- `genre` - Búsqueda por género (LIKE)
- `isbn` - Búsqueda por ISBN (LIKE)
- `publishedYear` - Año de publicación exacto
- `minRating` / `maxRating` - Rango de valoración
- `visible` - Filtrar por visibilidad (true/false)

### Orders Microservice (vía Gateway :8080)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/v1/orders` | Crear orden de compra |
| GET | `/api/v1/orders/{id}` | Obtener orden por ID |
| GET | `/api/v1/orders/user/{userId}` | Órdenes de un usuario |

### Transcripción POST (para máxima puntuación)

El Gateway soporta transcripción de peticiones POST a otros métodos HTTP:

| POST a | Se convierte en |
|--------|-----------------|
| `/api/v1/catalogue/books/{id}/update` | PUT `/api/v1/books/{id}` |
| `/api/v1/catalogue/books/{id}/patch` | PATCH `/api/v1/books/{id}` |
| `/api/v1/catalogue/books/{id}/delete` | DELETE `/api/v1/books/{id}` |

## Ejemplos de Uso (Postman/curl)

### Buscar libros de fantasía con rating > 4.5
```bash
curl "http://localhost:8080/api/v1/books?genre=Fantasy&minRating=4.5"
```

### Crear una orden
```bash
curl -X POST http://localhost:8080/api/v1/orders \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "userEmail": "test@example.com",
    "items": [
      {"bookId": 1, "quantity": 2},
      {"bookId": 3, "quantity": 1}
    ]
  }'
```

### Obtener órdenes de un usuario
```bash
curl "http://localhost:8080/api/v1/orders/user/1"
```

## Comunicación entre Microservicios

El microservicio Orders se comunica con Catalogue usando **Feign Client** y **Eureka**:

- NO usa IP ni puerto directamente
- Usa el nombre del servicio: `@FeignClient(name = "catalogue")`
- Eureka resuelve el nombre a la instancia disponible
- Soporta balanceo de carga del lado del cliente

## Estructura del Proyecto

```
Relatos_Papel_Spring/
├── src/                          # Catalogue Microservice
│   ├── main/java/.../catalogue/
│   │   ├── controller/           # REST Controllers
│   │   ├── service/              # Business Logic
│   │   ├── repository/           # JPA Repositories
│   │   ├── exception/            # Custom Exceptions
│   │   └── db/                   # SQL Scripts
│   └── resources/
│       └── application.yaml
├── eureka-server/                # Eureka Server
├── gateway/                      # Cloud Gateway
└── orders/                       # Orders Microservice
```

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Cloud 2023.0.1**
  - Netflix Eureka (Service Discovery)
  - Cloud Gateway (API Gateway)
  - OpenFeign (HTTP Client)
- **Spring Data JPA**
- **MySQL 8.0**
- **Lombok**

