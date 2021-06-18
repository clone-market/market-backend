package com.market.api.user.exceptionHandler;

import com.market.api.user.MemberController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.mail.MessagingException;

@Slf4j
@RestControllerAdvice(basePackageClasses = MemberController.class)
public class MemberApiExceptionHandler {

    public static void main(String[] args) {
    }

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

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<Void> handleMessagingException(MessagingException exception) {
        log.error("메일 전송실패: {}", exception.getCause().getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
