@echo off
echo ========================================
echo    CONECTAR PROYECTO CON GITHUB
echo ========================================
echo.
echo Repositorio destino:
echo https://github.com/CamilaParedes23/API-RESTful-de-Libros-.git
echo.

REM Verificar si Git está instalado
git --version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ❌ ERROR: Git no está instalado o no está en el PATH
    echo.
    echo Para instalar Git:
    echo 1. Descargar desde: https://git-scm.com/download/win
    echo 2. Instalar con opciones por defecto
    echo 3. Reiniciar terminal/VS Code
    echo 4. Ejecutar este script nuevamente
    echo.
    pause
    exit /b 1
)

echo ✅ Git detectado correctamente
echo.

REM Verificar si ya es un repositorio git
if exist .git (
    echo ⚠️  Ya existe un repositorio git local
    echo ¿Desea continuar? Esto podría sobrescribir la configuración actual.
    set /p continuar="Continuar? (s/n): "
    if /i not "%continuar%"=="s" (
        echo Operación cancelada
        pause
        exit /b 0
    )
) else (
    echo 📁 Inicializando repositorio git local...
    git init
    if %ERRORLEVEL% NEQ 0 (
        echo ❌ Error al inicializar repositorio git
        pause
        exit /b 1
    )
)

echo.
echo 📝 Configurando información de usuario git...
echo.

REM Pedir información del usuario
set /p nombre="Ingresa tu nombre para Git: "
set /p email="Ingresa tu email para Git: "

git config user.name "%nombre%"
git config user.email "%email%"

echo.
echo 📦 Agregando archivos al repositorio...
git add .

if %ERRORLEVEL% NEQ 0 (
    echo ❌ Error al agregar archivos
    pause
    exit /b 1
)

echo.
echo 💾 Creando commit inicial...
git commit -m "Initial commit: Complete Libros API with Docker - Spring Boot 3.4.12

Features implemented:
✅ Complete CRUD REST API for books
✅ Bean Validation with Spanish messages
✅ Global exception handling
✅ OpenAPI/Swagger documentation
✅ Spring Actuator health checks
✅ Multi-stage Docker optimization
✅ MySQL database integration
✅ Postman collection with 13 automated tests
✅ Complete technical documentation
✅ Automation scripts for deployment

Technologies: Java 17, Spring Boot 3.4.12, MySQL 8.0, Docker, Postman"

if %ERRORLEVEL% NEQ 0 (
    echo ❌ Error al crear commit
    pause
    exit /b 1
)

echo.
echo 🌐 Configurando repositorio remoto...
git remote remove origin 2>nul
git remote add origin https://github.com/CamilaParedes23/API-RESTful-de-Libros-.git

if %ERRORLEVEL% NEQ 0 (
    echo ❌ Error al agregar repositorio remoto
    pause
    exit /b 1
)

echo.
echo 🔄 Configurando rama main...
git branch -M main

echo.
echo 📡 Intentando hacer pull del repositorio remoto...
git pull origin main --allow-unrelated-histories --no-edit

if %ERRORLEVEL% NEQ 0 (
    echo ⚠️  No se pudo hacer pull (posiblemente el repo remoto está vacío)
    echo Continuando con push...
)

echo.
echo 🚀 Subiendo proyecto a GitHub...
git push -u origin main

if %ERRORLEVEL% NEQ 0 (
    echo ❌ Error al subir a GitHub
    echo.
    echo Posibles causas:
    echo 1. No tienes permisos en el repositorio
    echo 2. Necesitas autenticación (token personal)
    echo 3. El repositorio no existe o es privado
    echo.
    echo Para autenticación con token:
    echo 1. Ve a GitHub ^> Settings ^> Developer settings ^> Personal access tokens
    echo 2. Genera un nuevo token con permisos de repo
    echo 3. Usa tu usuario y el token como contraseña
    echo.
    pause
    exit /b 1
)

echo.
echo 🏷️  Creando tag de versión...
git tag -a v1.0.0 -m "Version 1.0.0 - API RESTful completa con Docker

Complete implementation:
- Spring Boot 3.4.12 REST API
- MySQL database integration
- Docker multi-stage deployment
- Comprehensive testing with Postman
- Professional technical documentation"

git push origin v1.0.0

if %ERRORLEVEL% NEQ 0 (
    echo ⚠️  No se pudo crear tag (pero el código se subió correctamente)
)

echo.
echo ========================================
echo           🎉 ¡ÉXITO! 🎉
echo ========================================
echo.
echo ✅ Proyecto subido exitosamente a GitHub
echo.
echo 📍 URLs importantes:
echo 🔗 Repositorio: https://github.com/CamilaParedes23/API-RESTful-de-Libros-
echo 📋 Issues: https://github.com/CamilaParedes23/API-RESTful-de-Libros-/issues
echo 🏷️  Releases: https://github.com/CamilaParedes23/API-RESTful-de-Libros-/releases
echo.
echo 📊 Próximos pasos:
echo 1. Verificar que todos los archivos se subieron correctamente
echo 2. Agregar descripción al repositorio en GitHub
echo 3. Configurar GitHub Pages si deseas documentación web
echo 4. Publicar imagen en Docker Hub
echo 5. Completar README con badges de GitHub
echo.
echo ¡Tu API RESTful está ahora en GitHub y lista para colaboración!
echo.
pause
