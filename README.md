# Microservicios: Product y Orders

Este proyecto implementa dos microservicios en Java con Spring Boot, PostgreSQL y Docker. Nos enfocamos en implementar buenas prácticas como autenticación básica, control de errores, separación de responsabilidades y sincronización entre servicios.

## Requisitos

- Java 17
- Maven
- Docker + Docker Compose
- Postman (opcional)

## Microservicios

- **product-service**: gestión de productos y categorías.
- **orders-service**: gestión de órdenes y validación de stock en tiempo real.


## Características destacadas

### Modelo Vista Controlador (MVC)
Cada microservicio sigue la arquitectura MVC:
- **Model**: entidades JPA que representan los datos.
- **DTO**: objetos para transferir datos con validaciones y estructura clara.
- **Mapper**: transforma entidades ↔ DTOs.
- **Controller**: define los endpoints HTTP.
- **Service**: contiene la lógica de negocio.
- **Repository**: accede a la base de datos.

### Autenticación básica
El sistema permite proteger los endpoints mediante autenticación básica configurable en un archivo separado de seguridad.

Las credenciales para ingresar son:
Usuario: user
Contraseña: 1234

### Manejo de errores
Todos los microservicios implementan control de errores:
- Validación de existencia de producto.
- Errores de stock insuficiente.
- Manejo de entradas inválidas.
- Respuestas con códigos HTTP.

### Sincronización automática de stock
Cuando se realiza una acción sobre una orden:
- `POST` ➝ se descuenta stock.
- `PUT` ➝ se actualiza el stock correctamente según los cambios (incrementa o decrementa).
- `DELETE` ➝ se repone el stock eliminado.

Esto mantiene integridad entre órdenes y productos.

### Documentación Swagger
Cada microservicio tiene su propia documentación accesible vía Swagger:

- [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html) → Product
- [`http://localhost:8084/swagger-ui.html`](http://localhost:8084/swagger-ui.html) → Orders


## Instrucciones para ejecutar

1. Cloná este repositorio o descomprimí el ZIP del proyecto.
2. Desde una terminal, posicionate en la carpeta `microservices/`.
3. Ejecutá:

```bash
docker-compose up --build
```

Esto levantará:
Servicio        - Puerto - Descripción
product-service - 8080   - Gestión de productos y categorías  
orders-service  - 8084   - Gestión de órdenes con validación  
product-db      - 5431   - PostgreSQL para productos 
order-db        - 5432   - PostgreSQL para órdenes             


## Endpoints útiles

### `product-service`
- `GET /api/products` – Listar productos (entidad)
- `GET /api/products/info` – Listar productos (DTO)
- `GET /api/products/{id}` – Obtener producto por id
- `GET /api/products/price` – Obtener productos ordenados por precio descendiente 
- `POST /api/products` – Crear producto
- `PUT /api/products/{id}` – Actualizar producto
- `DELETE /api/products/{id}` – Eliminar producto

- `GET /api/categories` – Listar categorías (entidad)
- `GET /api/categories/info` – Listar categorías (DTO)
- `GET /api/categories/{id}` – Obtener categoria por id
- `POST /api/categories` – Crear categoría
- `PUT /api/categories/{id}` – Actualizar categoría
- `DELETE /api/categories/{id}` – Eliminar categoría

### `orders-service`
- `GET /api/orders/info` – Listar órdenes
- `POST /api/orders` – Crear orden
- `PUT /api/orders/{id}` – Modificar orden
- `DELETE /api/orders/{id}` – Eliminar orden

## Estructura de carpetas

microservices/
├── docker-compose.yml
├── product/
│   └── src/main/java/com/ucc/product/
├── orders/
│   └── src/main/java/com/ucc/orders/

## Autores

Proyecto desarrollado por Valentina Yafar y Carolina Dominguez
