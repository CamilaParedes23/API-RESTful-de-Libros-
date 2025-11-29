package com.espe.test.test.services;

import com.espe.test.test.exception.LibroNotFoundException;
import com.espe.test.test.model.entities.Libro;
import com.espe.test.test.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroServiceImpl implements LibroService{

    private final LibroRepository repository;

    @Autowired
    public LibroServiceImpl(LibroRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Libro> buscarTodos() {
        return (List<Libro>) repository.findAll();
    }

    @Override
    public Optional<Libro> buscarPorID(Long id) {
        return repository.findById(id);
    }

    @Override
    public Libro guardar(Libro libro) {
        return repository.save(libro);
    }

    @Override
    public void eliminarPorId(Long id) {
        if (!repository.existsById(id)) {
            throw new LibroNotFoundException(id);
        }
        repository.deleteById(id);
    }

    public Libro actualizarLibro(Long id, Libro libroActualizado) {
        return repository.findById(id)
                .map(libro -> {
                    libro.setTitulo(libroActualizado.getTitulo());
                    libro.setAutor(libroActualizado.getAutor());
                    libro.setGenero(libroActualizado.getGenero());
                    return repository.save(libro);
                })
                .orElseThrow(() -> new LibroNotFoundException(id));
    }
}
