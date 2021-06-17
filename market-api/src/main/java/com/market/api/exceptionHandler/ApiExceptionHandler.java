package com.market.api.exceptionHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorParam> handleValidationException(MethodArgumentNotValidException exception) {
        ErrorParam errorParam = new ErrorParam("입력값이 유효하지 않습니다.");

        exception.getBindingResult()
                .getAllErrors()
                .forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errorParam.getErrors().put(fieldName, errorMessage);
                });
        return ResponseEntity.badRequest().body(errorParam);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorParam> handleValidationException(HttpMessageNotReadableException exception) {
        ErrorParam errorParam = new ErrorParam("입력값이 없거나 형식이 올바르지 않습니다.");
        return ResponseEntity.badRequest().body(errorParam);
    }
}
