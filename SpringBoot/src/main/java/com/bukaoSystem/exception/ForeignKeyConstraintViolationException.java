package com.bukaoSystem.exception;

public class ForeignKeyConstraintViolationException extends RuntimeException {
    public ForeignKeyConstraintViolationException(String message) {
        super(message);
    }
}
