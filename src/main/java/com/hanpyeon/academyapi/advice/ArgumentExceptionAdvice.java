package com.hanpyeon.academyapi.advice;

import com.hanpyeon.academyapi.account.ExceptionResponseBody;
import com.hanpyeon.academyapi.account.exceptions.AlreadyRegisteredException;
import com.hanpyeon.academyapi.account.exceptions.MemberRoleVerificationException;
import com.hanpyeon.academyapi.account.exceptions.NotSupportedMemberTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ArgumentExceptionAdvice {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> requestFormatExceptionHandler(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest().body("API 호출을 위해 필수적인 필드가 있습니다.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> missingFieldExceptionHandler(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getFieldErrors().stream()
                .map(o -> o.getField())
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(new ExceptionResponseBody("-001", errors));
    }

    @ExceptionHandler(AlreadyRegisteredException.class)
    public ResponseEntity<?> alreadyRegisteredExceptionHandler(AlreadyRegisteredException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponseBody("-002", List.of(exception.getMessage())));
    }

    @ExceptionHandler(MemberRoleVerificationException.class)
    public ResponseEntity<?> roleVerificationExceptionHandler(MemberRoleVerificationException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponseBody("-003", List.of(exception.getMessage())));
    }

    @ExceptionHandler(NotSupportedMemberTypeException.class)
    public ResponseEntity<?> notSupportedMemberTypeExceptionHandler(NotSupportedMemberTypeException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponseBody("-004", List.of(exception.getMessage())));
    }
}
