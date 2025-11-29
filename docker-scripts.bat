@echo off
echo ========================================
echo      SCRIPTS DE DOCKERIZACION - LIBROS API
echo ========================================
echo.
echo Seleccione una opcion:
echo 1. Construir imagen de la API
echo 2. Ejecutar contenedor MySQL
echo 3. Ejecutar aplicacion con perfil Docker
echo 4. Ejecutar aplicacion completa (MySQL + API)
echo 5. Limpiar contenedores
echo 6. Salir
echo.
set /p option="Ingrese su opcion (1-6): "

if "%option%"=="1" goto build_api
if "%option%"=="2" goto run_mysql
if "%option%"=="3" goto run_api_docker
if "%option%"=="4" goto run_complete
if "%option%"=="5" goto cleanup
if "%option%"=="6" goto exit
goto menu

:build_api
echo.
echo Construyendo imagen de la API...
docker build -t libros-api:latest .
if %ERRORLEVEL% EQU 0 (
    echo Imagen construida exitosamente!
) else (
    echo Error al construir la imagen
)
pause
goto menu

:run_mysql
echo.
echo Ejecutando contenedor MySQL...
docker run --name mysql-libros -e MYSQL_ROOT_PASSWORD=rootpassword -e MYSQL_DATABASE=librosdb -p 3306:3306 -d mysql:8.0
if %ERRORLEVEL% EQU 0 (
    echo Contenedor MySQL iniciado exitosamente!
    echo Usuario: root
    echo Password: rootpassword
    echo Base de datos: librosdb
    echo Puerto: 3306
) else (
    echo Error al iniciar contenedor MySQL
)
pause
goto menu

:run_api_docker
echo.
echo Ejecutando API con perfil Docker...
docker run --name libros-api-container --link mysql-libros:mysql-db -p 8080:8080 -e SPRING_PROFILES_ACTIVE=docker libros-api:latest
pause
goto menu

:run_complete
echo.
echo Ejecutando aplicacion completa...
echo 1. Iniciando MySQL...
docker run --name mysql-libros -e MYSQL_ROOT_PASSWORD=rootpassword -e MYSQL_DATABASE=librosdb -p 3306:3306 -d mysql:8.0
timeout /t 10 /nobreak >nul
echo 2. Iniciando API...
docker run --name libros-api-container --link mysql-libros:mysql-db -p 8080:8080 -e SPRING_PROFILES_ACTIVE=docker libros-api:latest
pause
goto menu

:cleanup
echo.
echo Limpiando contenedores...
docker stop libros-api-container mysql-libros 2>nul
docker rm libros-api-container mysql-libros 2>nul
echo Contenedores eliminados
pause
goto menu

:menu
cls
goto start

:exit
echo.
echo Gracias por usar el sistema de dockerizacion!
exit /b

:start
