package com.espe.test.test.exception;

public class LibroNotFoundException extends RuntimeException {
    public LibroNotFoundException(Long id) {
        super("Libro con ID " + id + " no encontrado");
    }

    public LibroNotFoundException(String message) {
        super(message);
    }
}
