package com.astrapay.exception;

public class NoteTitleAlreadyExistsException extends RuntimeException {

    public NoteTitleAlreadyExistsException(String title) {
        super("Note dengan title '" + title + "' sudah ada");
    }
}
