package com.espe.test.test.exception;

import com.espe.test.test.model.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

/**
 * Global Exception Handler compatible con Swagger / SpringDoc.
 * Limita su alcance únicamente a los controladores de tu proyecto
 * y evita interceptar las rutas internas usadas por Swagger.
 */
@RestControllerAdvice(basePackages = "com.espe.test.test")
public class GlobalExceptionHandler {

    /** Manejo de errores de validación (Bean Validation) */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });

        return ResponseEntity.badRequest().body(
                new ApiResponse<>(
                        false,
                        "Error de validación en los datos proporcionados",
                        errors
                )
        );
    }

    /** Manejo de errores de tipo incorrecto en parámetros */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<String>> handleTypeMismatchException(
            MethodArgumentTypeMismatchException ex) {

        String message = String.format(
                "El parámetro '%s' debe ser de tipo %s",
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconocido"
        );

        return ResponseEntity.badRequest().body(ApiResponse.error(message));
    }

    /** Manejo de Libro no encontrado */
    @ExceptionHandler(LibroNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleLibroNotFoundException(LibroNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage()));
    }

    /**
     * Manejo genérico de excepciones sin interferir con Swagger.
     * Ignora las rutas internas de OpenAPI para evitar romper /api-docs.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGenericException(Exception ex) {

        String msg = ex.getMessage() != null ? ex.getMessage() : ex.toString();

        // Evitar interferencia con SpringDoc / Swagger
        if (msg.contains("springdoc") || msg.contains("OpenAPI") || msg.contains("swagger")) {
            throw new RuntimeException(ex); // permite que Spring maneje este error
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error interno del servidor: " + msg));
    }
}
