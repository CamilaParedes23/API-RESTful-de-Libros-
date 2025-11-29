package com.espe.test.test.services;

import com.espe.test.test.model.entities.Libro;

import java.util.List;
import java.util.Optional;

public interface LibroService {
    List<Libro> buscarTodos();
    Optional<Libro> buscarPorID(Long id);
    Libro guardar(Libro libro);
    void eliminarPorId(Long id);
    Libro actualizarLibro(Long id, Libro libroActualizado);
}
