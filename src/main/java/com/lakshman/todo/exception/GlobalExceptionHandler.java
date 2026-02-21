package com.lakshman.todo.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lakshman.todo.common.dto.ApiResponseWithErrors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseWithErrors<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        ApiResponseWithErrors<Void> allErrors = new ApiResponseWithErrors<>();
        allErrors.setData(null);
        allErrors.setErrors(errors);
        allErrors.setMessage("Validation failed please fill the required details");
        allErrors.setSuccess(false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(allErrors);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ApiResponseWithErrors<Void>> handleInsufficientAuthenticationException(
            InsufficientAuthenticationException ex) {
        ApiResponseWithErrors<Void> allErrors = new ApiResponseWithErrors<>();
        allErrors.setData(null);
        allErrors.setMessage(ex.getMessage());
        allErrors.setSuccess(false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(allErrors);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponseWithErrors<Void>> handleRuntimeException(RuntimeException ex) {
        ApiResponseWithErrors<Void> allErrors = new ApiResponseWithErrors<>();
        allErrors.setData(null);
        allErrors.setMessage("Something went wrong- " + ex.getMessage());
        allErrors.setSuccess(false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(allErrors);
    }
}