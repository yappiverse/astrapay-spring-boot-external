package com.astrapay.exception;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException(Long id) {
        super("Note dengan id " + id + " tidak ditemukan");
    }
}
