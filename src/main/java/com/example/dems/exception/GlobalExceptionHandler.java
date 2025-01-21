package com.example.dems.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
/**
 * GlobalExceptionHandler is a controller advice which handles exceptions thrown by the application.
 * It captures the following exceptions:
 * <ul>
 *     <li>NotFound: returns a 404 response with a message</li>
 *     <li>MethodArgumentNotValidException: returns a 400 response with a map of field errors</li>
 *     <li>ConstraintViolationException: returns a 400 response with a map of field errors</li>
 *     <li>Exception: returns a 500 response with a message</li>
 * </ul>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<Map<String, String>> handleNotFound(NotFound e){
        return ResponseEntity.status(404).body(Map.of("message", e.getMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Internal Server Error");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
