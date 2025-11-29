@echo off
echo ========================================
echo        EJECUTAR LIBROS API LOCAL
echo ========================================
echo.
echo Iniciando aplicacion Spring Boot en puerto 8081...
echo Documentacion disponible en: http://localhost:8081/swagger-ui.html
echo API endpoints en: http://localhost:8081/api/v1/libros
echo Health check en: http://localhost:8081/actuator/health
echo.
java -jar target\test-0.0.1-SNAPSHOT.jar
pause

