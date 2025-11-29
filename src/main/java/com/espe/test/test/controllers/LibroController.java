package com.espe.test.test.controllers;

import com.espe.test.test.exception.LibroNotFoundException;
import com.espe.test.test.model.dto.ApiResponse;
import com.espe.test.test.model.entities.Libro;
import com.espe.test.test.services.LibroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/libros")
@Tag(name = "Libros", description = "API para la gestión de libros")
public class LibroController {

    private final LibroService service;

    @Autowired
    public LibroController(LibroService service) {
        this.service = service;
    }

    @Operation(summary = "Obtener todos los libros", description = "Retorna una lista de todos los libros disponibles")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista de libros obtenida exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<Libro>>> listarTodos() {
        List<Libro> libros = service.buscarTodos();
        ApiResponse<List<Libro>> response = ApiResponse.success("Libros obtenidos exitosamente", libros);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar libro por ID", description = "Retorna un libro específico basado en su ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Libro encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Libro no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "ID inválido")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Libro>> buscarPorId(
            @Parameter(description = "ID del libro a buscar") @PathVariable Long id) {
        return service.buscarPorID(id)
                .map(libro -> {
                    ApiResponse<Libro> response = ApiResponse.success("Libro encontrado", libro);
                    return ResponseEntity.ok(response);
                })
                .orElseThrow(() -> new LibroNotFoundException(id));
    }

    @Operation(summary = "Crear un nuevo libro", description = "Crea un nuevo libro con la información proporcionada")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Libro creado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<Libro>> crear(
            @Valid @RequestBody @Parameter(description = "Datos del libro a crear") Libro libro) {
        Libro nuevoLibro = service.guardar(libro);
        ApiResponse<Libro> response = ApiResponse.success("Libro creado exitosamente", nuevoLibro);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Actualizar un libro existente", description = "Actualiza un libro existente con nueva información")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Libro actualizado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Libro no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Libro>> actualizar(
            @Parameter(description = "ID del libro a actualizar") @PathVariable Long id,
            @Valid @RequestBody @Parameter(description = "Nuevos datos del libro") Libro libroActualizado) {

        Libro libro = service.actualizarLibro(id, libroActualizado);
        ApiResponse<Libro> response = ApiResponse.success("Libro actualizado exitosamente", libro);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar un libro", description = "Elimina un libro específico basado en su ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Libro eliminado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Libro no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "ID inválido")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> eliminarPorId(
            @Parameter(description = "ID del libro a eliminar") @PathVariable Long id) {
        service.eliminarPorId(id);
        ApiResponse<String> response = ApiResponse.success("Libro eliminado exitosamente", null);
        return ResponseEntity.ok(response);
    }
}
