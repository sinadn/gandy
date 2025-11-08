package com.example.gandy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class DeleteImageException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DeleteImageException(String shortMessage, String message) {
        super(String.format("Failed for [%s]: %s", shortMessage, message));
    }
}