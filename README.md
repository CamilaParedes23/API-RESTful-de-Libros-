# API RESTful de Libros - Spring Boot + Docker

## Descripción General

API REST completa para la gestión de libros desarrollada con Spring Boot 3.4.12 y Java 17. Implementa operaciones CRUD completas, validaciones, manejo de errores, documentación automática con OpenAPI/Swagger, y está completamente dockerizada con base de datos MySQL.

## Arquitectura del Sistema

```
┌─────────────────────┐
│    Cliente HTTP     │
│  (Postman, Browser) │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│   Nginx (Opcional)  │
│  Load Balancer/     │
│  Reverse Proxy      │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│   Spring Boot API   │
│   Puerto: 8080/8081 │
│   - Controllers     │
│   - Services        │
│   - Repositories    │
│   - Validaciones    │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│   Base de Datos     │
│   MySQL 8.0         │
│   Puerto: 3306      │
└─────────────────────┘
```

### Capas de la Aplicación

1. **Capa de Presentación (Controllers)**
   - `LibroController`: Maneja las peticiones HTTP
   - Validaciones de entrada con Bean Validation
   - Respuestas estandarizadas con `ApiResponse<T>`
   - Documentación automática con OpenAPI/Swagger

2. **Capa de Negocio (Services)**
   - `LibroService`: Interfaz de servicios
   - `LibroServiceImpl`: Implementación de lógica de negocio
   - Manejo de excepciones personalizadas

3. **Capa de Acceso a Datos (Repositories)**
   - `LibroRepository`: Extiende CrudRepository
   - Operaciones CRUD automáticas con Spring Data JPA

4. **Capa de Persistencia**
   - Entidad `Libro` con validaciones JPA
   - Base de datos MySQL

## Características Implementadas

### ✅ Funcionalidades Core
- [x] CRUD completo de libros
- [x] Validaciones de entrada (Bean Validation)
- [x] Manejo global de excepciones
- [x] Respuestas API estandarizadas
- [x] Documentación automática (Swagger/OpenAPI)
- [x] Health checks (Spring Actuator)
- [x] Logging estructurado

### ✅ Containerización Docker
- [x] Dockerfile optimizado multi-stage
- [x] Imagen base Alpine (ligera)
- [x] Usuario no-root por seguridad
- [x] Variables de entorno configurables
- [x] Scripts de automatización

### ✅ Base de Datos
- [x] MySQL 8.0
- [x] Configuraciones para local y Docker
- [x] Esquema auto-generado (DDL)
- [x] Pool de conexiones optimizado

## Endpoints de la API

### Base URL
- **Local**: `http://localhost:8081/api/v1/libros`
- **Docker**: `http://localhost:8080/api/v1/libros`

### Operaciones CRUD

| Método | Endpoint | Descripción | Códigos de Estado |
|--------|----------|-------------|-------------------|
| GET    | `/`      | Obtener todos los libros | 200, 500 |
| GET    | `/{id}`  | Obtener libro por ID | 200, 404, 400 |
| POST   | `/`      | Crear nuevo libro | 201, 400, 500 |
| PUT    | `/{id}`  | Actualizar libro existente | 200, 404, 400 |
| DELETE | `/{id}`  | Eliminar libro | 200, 404, 400 |

### Estructura de la Entidad Libro

```json
{
  "id": 1,
  "titulo": "Cien años de soledad",
  "autor": "Gabriel García Márquez",
  "genero": "Realismo mágico"
}
```

### Validaciones

- **titulo**: Requerido, 1-200 caracteres
- **autor**: Requerido, 1-150 caracteres
- **genero**: Requerido, 1-50 caracteres

### Ejemplo de Respuesta API

```json
{
  "success": true,
  "message": "Libro creado exitosamente",
  "data": {
    "id": 1,
    "titulo": "Cien años de soledad",
    "autor": "Gabriel García Márquez",
    "genero": "Realismo mágico"
  },
  "timestamp": "2025-11-29T11:30:00"
}
```

## Configuración y Ejecución

### Prerrequisitos

- Java 17 o superior
- Maven 3.6+
- Docker y Docker Compose (para containerización)
- MySQL 8.0 (para ejecución local)

### 1. Ejecución Local

```bash
# 1. Clonar el repositorio
git clone <repository-url>
cd test

# 2. Configurar base de datos MySQL
# Crear base de datos: sisdb2025
# Usuario: AppRoot, Password: abcd, Puerto: 3307

# 3. Compilar y ejecutar
./mvnw clean package
java -jar target/test-0.0.1-SNAPSHOT.jar

# O usar el script
./run-local.bat
```

**URLs disponibles:**
- API: http://localhost:8081/api/v1/libros
- Documentación: http://localhost:8081/swagger-ui.html
- Health Check: http://localhost:8081/actuator/health

### 2. Ejecución con Docker


```bash
# 1. Construir imagen de la API
docker build -t libros-api:latest .

# 2. Ejecutar MySQL
docker run --name mysql-libros \
  -e MYSQL_ROOT_PASSWORD=rootpassword \
  -e MYSQL_DATABASE=librosdb \
  -p 3306:3306 -d mysql:8.0

# 3. Ejecutar API (después de unos segundos)
docker run --name libros-api-container \
  --link mysql-libros:mysql-db \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=docker \
  libros-api:latest
```


**URLs disponibles:**
- API: http://localhost:8080/api/v1/libros
- Health Check: http://localhost:8080/actuator/health

### 3. Limpieza de Contenedores

```bash
# Detener y eliminar contenedores
docker stop libros-api-container mysql-libros
docker rm libros-api-container mysql-libros

# Eliminar imagen
docker rmi libros-api:latest
```

## Pruebas con Postman

### Colección de Pruebas

Se incluye una colección completa de Postman con:

1. **Tests de CRUD exitosos**
   - Crear libro
   - Listar todos los libros
   - Buscar libro por ID
   - Actualizar libro
   - Eliminar libro

2. **Tests de casos de error**
   - Validaciones de entrada
   - Libro no encontrado
   - IDs inválidos
   - Errores del servidor

3. **Variables de entorno**
   - `{{base_url}}`: URL base de la API
   - `{{libro_id}}`: ID dinámico para pruebas

### Ejecución de Pruebas

1. Importar `postman-collection.json`
2. Configurar variables de entorno
3. Ejecutar colección completa o tests individuales
4. Verificar assertions automáticas

## Documentación Técnica

### Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Java | 17 | Runtime de aplicación |
| Spring Boot | 3.4.12 | Framework principal |
| Spring Data JPA | 3.4.12 | Acceso a datos |
| MySQL | 8.0 | Base de datos |
| Docker | Latest | Containerización |
| Maven | 3.9+ | Gestión de dependencias |
| SpringDoc OpenAPI | 2.2.0 | Documentación API |
| Hibernate Validator | 8.0.3 | Validaciones |

### Configuraciones Importantes

#### application.properties (Local)
```properties
spring.application.name=libros-api
server.port=8081
spring.datasource.url=jdbc:mysql://localhost:3307/sisdb2025
spring.datasource.username=AppRoot
spring.datasource.password=abcd
```

#### application-docker.properties (Docker)
```properties
server.port=8080
spring.datasource.url=jdbc:mysql://mysql-db:3306/librosdb
spring.datasource.username=root
spring.datasource.password=rootpassword

```

### Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/espe/test/test/
│   │   ├── TestApplication.java
│   │   ├── controllers/
│   │   │   └── LibroController.java
│   │   ├── services/
│   │   │   ├── LibroService.java
│   │   │   └── LibroServiceImpl.java
│   │   ├── repositories/
│   │   │   └── LibroRepository.java
│   │   ├── model/
│   │   │   ├── entities/
│   │   │   │   └── Libro.java
│   │   │   └── dto/
│   │   │       └── ApiResponse.java
│   │   └── exception/
│   │       ├── GlobalExceptionHandler.java
│   │       └── LibroNotFoundException.java
│   └── resources/
│       ├── application.properties
│       └── application-docker.properties
├── Dockerfile
├── .dockerignore
```

## Monitoreo y Observabilidad

### Health Checks
- Endpoint: `/actuator/health`
- Verifica conectividad a base de datos
- Estado de la aplicación

### Métricas
- Endpoint: `/actuator/metrics`
- Métricas de rendimiento
- Uso de memoria y CPU

### Logging
- Logs estructurados en consola
- Nivel DEBUG para desarrollo
- Trazabilidad de operaciones

## Seguridad

### Medidas Implementadas
- Usuario no-root en contenedor Docker
- Validaciones de entrada exhaustivas
- Manejo seguro de excepciones
- No exposición de stacktraces en producción

### Recomendaciones Adicionales
- Implementar HTTPS
- Agregar autenticación JWT
- Rate limiting
- CORS configurado
- Auditoría de operaciones

## Performance y Escalabilidad

### Optimizaciones Actuales
- Pool de conexiones configurado
- Imagen Docker multi-stage
- JPA lazy loading
- Respuestas paginadas (para implementar)

### Mejoras Futuras
- Cache con Redis
- Load balancing
- Métricas avanzadas
- Base de datos réplica para lectura

## Deployment

### Docker Hub
```bash
# Tag de la imagen
docker tag libros-api:latest username/libros-api:v1.0.0

# Push a Docker Hub
docker push username/libros-api:v1.0.0
```

### Producción
- Usar orquestadores como Kubernetes
- Configurar secrets para credenciales
- Monitoring con Prometheus/Grafana
- Backup automatizado de BD

## Troubleshooting

### Problemas Comunes

1. **Error de conexión a MySQL**
   - Verificar que MySQL esté ejecutándose
   - Revisar credenciales en application.properties
   - Esperar inicialización completa del contenedor

2. **Puerto en uso**
   - Cambiar puerto en application.properties
   - Verificar procesos con `netstat -an | findstr :8080`

3. **Error de build Docker**
   - Verificar que Docker esté ejecutándose
   - Revisar sintaxis del Dockerfile
   - Limpiar caché: `docker system prune`

### Logs Útiles
```bash
# Logs del contenedor API
docker logs libros-api-container

# Logs del contenedor MySQL
docker logs mysql-libros

# Seguir logs en tiempo real
docker logs -f libros-api-container
```

## Repositorio GitHub

### 🔗 Enlaces Importantes
- **Repositorio**: https://github.com/CamilaParedes23/API-RESTful-de-Libros-
- **Issues**: https://github.com/CamilaParedes23/API-RESTful-de-Libros-/issues
- **Releases**: https://github.com/CamilaParedes23/API-RESTful-de-Libros-/releases

### 📦 Clonar el Proyecto
```bash
git clone https://github.com/CamilaParedes23/API-RESTful-de-Libros-.git
cd API-RESTful-de-Libros-
```

## Contribución

### Proceso de Desarrollo
1. Fork del repositorio: https://github.com/CamilaParedes23/API-RESTful-de-Libros-
2. Crear feature branch: `git checkout -b feature/nueva-funcionalidad`
3. Implementar cambios con tests
4. Commit: `git commit -m "Add: nueva funcionalidad"`
5. Push: `git push origin feature/nueva-funcionalidad`
6. Crear Pull Request

### Estándares de Código
- Seguir convenciones de Spring Boot
- Documentar APIs con OpenAPI
- Tests unitarios para nuevas funciones
- Validar con Postman collection

## Licencia

[MIT License](LICENSE)

## Contacto

- **Desarrolladora**: Camila Paredes
- **GitHub**: https://github.com/CamilaParedes23
- **Repositorio**: https://github.com/CamilaParedes23/API-RESTful-de-Libros-

---

## Changelog

### v1.0.0 (2025-11-29)
- ✅ Implementación inicial de CRUD
- ✅ Validaciones Bean Validation
- ✅ Dockerización completa
- ✅ Documentación OpenAPI
- ✅ Manejo global de excepciones
- ✅ Scripts de automatización
