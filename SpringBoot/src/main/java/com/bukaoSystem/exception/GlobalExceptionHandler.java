package com.bukaoSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountAlreadyRegisteredException.class)
    public ResponseEntity<String> handleAccountAlreadyRegisteredException(AccountAlreadyRegisteredException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ForeignKeyConstraintViolationException.class)
    public ResponseEntity<String> handleForeignKeyConstraintViolation(ForeignKeyConstraintViolationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

}
