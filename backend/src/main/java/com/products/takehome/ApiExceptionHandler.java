package com.products.takehome;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice 
public class ApiExceptionHandler {
    
    @ExceptionHandler (MethodArgumentNotValidException.class)
    public ResponseEntity<List<FieldMessage>> handleValidation(MethodArgumentNotValidException ex) {
        List<FieldMessage> errors = new ArrayList<>();
        for (FieldError error : ex.getFieldErrors()) {
            errors.add(new FieldMessage(error.getField(), error.getDefaultMessage()));
        }
        return ResponseEntity.badRequest().body(errors);
    }
}
