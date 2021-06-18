package com.market.api.user.exceptionHandler;

import com.market.api.user.MemberController;
import com.market.member.exception.EnumValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<Void> handleMessagingException(MessagingException exception) {
        log.error("메일 전송실패: {}", exception.getCause().getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ErrorParam> handleIllegalException(RuntimeException exception) {
        ErrorParam errorParam = new ErrorParam(exception.getMessage());
        return ResponseEntity.badRequest().body(errorParam);
    }

    @ExceptionHandler(EnumValidationException.class)
    public ResponseEntity<ErrorParam> handleEnumValidationException(EnumValidationException exception) {
        ErrorParam errorParam = new ErrorParam(String.format("'%s' 는 올바르지 동의형식입니다.", exception.getValue()));
        return ResponseEntity.badRequest().body(errorParam);
    }
}
