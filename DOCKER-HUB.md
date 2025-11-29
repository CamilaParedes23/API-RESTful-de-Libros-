# Instrucciones para Docker Hub

## Preparación

1. **Crear cuenta en Docker Hub**: https://hub.docker.com/
2. **Iniciar sesión en Docker Desktop**
3. **Verificar que Docker esté ejecutándose**:
   ```bash
   docker --version
   docker info
   ```

## Construcción y Publicación

### 1. Construir la imagen localmente

```bash
# Asegurarse de estar en el directorio del proyecto
cd C:\Users\usuario\Documents\UNIVERSIDAD\7MO\DISTRIBUIDAS\SEGUNDO\PROYECTOS\test\test

# Construir la imagen
docker build -t libros-api:latest .

# Verificar que la imagen se creó correctamente
docker images | findstr libros-api
```

### 2. Probar la imagen localmente

```bash
# Iniciar MySQL
docker run --name mysql-libros \
  -e MYSQL_ROOT_PASSWORD=rootpassword \
  -e MYSQL_DATABASE=librosdb \
  -p 3306:3306 -d mysql:8.0

# Esperar unos segundos para que MySQL se inicialice
timeout /t 15 /nobreak

# Iniciar la aplicación
docker run --name libros-api-container \
  --link mysql-libros:mysql-db \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=docker \
  libros-api:latest

# Probar que funciona
# Abrir http://localhost:8080/swagger-ui.html
```

### 3. Etiquetar para Docker Hub

```bash
# Reemplazar 'tu-usuario' con tu nombre de usuario de Docker Hub
docker tag libros-api:latest tu-usuario/libros-api:v1.0.0
docker tag libros-api:latest tu-usuario/libros-api:latest

# Ejemplo con un usuario ficticio:
# docker tag libros-api:latest johndoe/libros-api:v1.0.0
# docker tag libros-api:latest johndoe/libros-api:latest
```

### 4. Subir a Docker Hub

```bash
# Iniciar sesión (solo la primera vez)
docker login

# Publicar la imagen
docker push tu-usuario/libros-api:v1.0.0
docker push tu-usuario/libros-api:latest

# Ejemplo:
# docker push johndoe/libros-api:v1.0.0
# docker push johndoe/libros-api:latest
```

### 5. Verificar publicación

1. Ir a https://hub.docker.com/
2. Verificar que el repositorio `libros-api` esté visible
3. Verificar que tengas las tags `v1.0.0` y `latest`

### 6. Probar descarga desde Docker Hub

```bash
# Eliminar imagen local (para probar descarga)
docker rmi tu-usuario/libros-api:latest

# Descargar desde Docker Hub
docker pull tu-usuario/libros-api:latest

# Ejecutar desde Docker Hub
docker run --name libros-api-hub \
  --link mysql-libros:mysql-db \
  -p 8081:8080 \
  -e SPRING_PROFILES_ACTIVE=docker \
  tu-usuario/libros-api:latest
```

## Descripción para Docker Hub Repository

### Título
```
Libros API - Spring Boot REST API with MySQL
```

### Descripción Corta
```
Complete RESTful API for book management built with Spring Boot 3.4.12, Java 17, and MySQL. Features CRUD operations, validation, error handling, and OpenAPI documentation.
```

### Descripción Completa

```markdown
# 📚 Libros API - RESTful Book Management System

A complete REST API for book management built with modern Java technologies.

## 🚀 Features

- **Complete CRUD Operations**: Create, Read, Update, Delete books
- **Data Validation**: Bean Validation with custom error messages
- **Error Handling**: Global exception handling with standardized responses
- **API Documentation**: Automatic OpenAPI/Swagger documentation
- **Health Monitoring**: Spring Actuator endpoints
- **Database Integration**: MySQL 8.0 with JPA/Hibernate
- **Docker Ready**: Multi-stage Dockerfile with Alpine Linux

## 🛠️ Technologies

- **Java 17**
- **Spring Boot 3.4.12**
- **Spring Data JPA**
- **MySQL 8.0**
- **Docker & Alpine Linux**
- **OpenAPI 3 (Swagger)**
- **Maven**

## 🏃‍♂️ Quick Start

### Using Docker (Recommended)

1. **Start MySQL:**
```bash
docker run --name mysql-libros \
  -e MYSQL_ROOT_PASSWORD=rootpassword \
  -e MYSQL_DATABASE=librosdb \
  -p 3306:3306 -d mysql:8.0
```

2. **Start API:**
```bash
docker run --name libros-api \
  --link mysql-libros:mysql-db \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=docker \
  tu-usuario/libros-api:latest
```

3. **Access API:**
   - API Base: http://localhost:8080/api/v1/libros
   - Documentation: http://localhost:8080/swagger-ui.html
   - Health Check: http://localhost:8080/actuator/health

## 📋 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/api/v1/libros` | List all books |
| GET    | `/api/v1/libros/{id}` | Get book by ID |
| POST   | `/api/v1/libros` | Create new book |
| PUT    | `/api/v1/libros/{id}` | Update book |
| DELETE | `/api/v1/libros/{id}` | Delete book |

## 📝 Book Entity

```json
{
  "id": 1,
  "titulo": "Cien años de soledad",
  "autor": "Gabriel García Márquez",
  "genero": "Realismo mágico"
}
```

## 🔧 Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `SPRING_PROFILES_ACTIVE` | `default` | Application profile |
| `MYSQL_HOST` | `mysql-db` | MySQL hostname |
| `MYSQL_PORT` | `3306` | MySQL port |
| `MYSQL_DATABASE` | `librosdb` | Database name |
| `MYSQL_USERNAME` | `root` | Database username |
| `MYSQL_PASSWORD` | `rootpassword` | Database password |

## 📊 Health & Monitoring

- **Health Check**: `/actuator/health`
- **Application Info**: `/actuator/info`
- **Metrics**: `/actuator/metrics`

## 🔐 Security Features

- Non-root user in container
- Input validation with Bean Validation
- Global exception handling
- SQL injection protection via JPA

## 📖 Documentation

Complete API documentation available at `/swagger-ui.html` when running.

## 🤝 Contributing

1. Fork the repository
2. Create feature branch
3. Commit changes
4. Push to branch
5. Create Pull Request

## 📄 License

MIT License - see LICENSE file for details.

---

**Repository**: https://github.com/your-username/libros-api
**Issues**: https://github.com/your-username/libros-api/issues
```

### Tags sugeridas
```
spring-boot, java, rest-api, mysql, docker, crud, swagger, openapi, jpa, hibernate, maven, microservices, api-rest, spring-data
```

## Script de publicación automatizada

```bash
#!/bin/bash
# publish-to-dockerhub.sh

set -e

# Variables
DOCKER_USER="tu-usuario"
IMAGE_NAME="libros-api"
VERSION="v1.0.0"

echo "🚀 Iniciando publicación a Docker Hub..."

# Construir imagen
echo "📦 Construyendo imagen..."
docker build -t $IMAGE_NAME:latest .

# Etiquetar
echo "🏷️  Etiquetando imagen..."
docker tag $IMAGE_NAME:latest $DOCKER_USER/$IMAGE_NAME:$VERSION
docker tag $IMAGE_NAME:latest $DOCKER_USER/$IMAGE_NAME:latest

# Verificar login
echo "🔐 Verificando login a Docker Hub..."
docker info | grep Username || docker login

# Publicar
echo "📤 Publicando a Docker Hub..."
docker push $DOCKER_USER/$IMAGE_NAME:$VERSION
docker push $DOCKER_USER/$IMAGE_NAME:latest

echo "✅ ¡Publicación completada!"
echo "📝 Repositorio: https://hub.docker.com/r/$DOCKER_USER/$IMAGE_NAME"
```
