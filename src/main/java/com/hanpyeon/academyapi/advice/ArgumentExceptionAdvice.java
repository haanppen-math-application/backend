package com.hanpyeon.academyapi.advice;

import com.hanpyeon.academyapi.account.exceptions.AlreadyRegisteredException;
import com.hanpyeon.academyapi.account.exceptions.MemberRegisterRequestVerificationException;
import com.hanpyeon.academyapi.account.exceptions.NoSuchMemberException;
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
    public ResponseEntity<?> requestFormatExceptionHandler(final HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponseBody("-000", List.of("해당 API에 적절하지 않은 요청 형식입니다.")));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> missingFieldExceptionHandler(final MethodArgumentNotValidException exception) {
        List<String> errors = exception.getFieldErrors().stream()
                .map(o -> o.getField())
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(new ExceptionResponseBody("-001", errors));
    }

    @ExceptionHandler(AlreadyRegisteredException.class)
    public ResponseEntity<?> alreadyRegisteredExceptionHandler(final AlreadyRegisteredException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponseBody("-002", List.of(exception.getMessage())));
    }

    @ExceptionHandler(MemberRegisterRequestVerificationException.class)
    public ResponseEntity<?> roleVerificationExceptionHandler(final MemberRegisterRequestVerificationException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponseBody("-003", List.of(exception.getMessage())));
    }

    @ExceptionHandler(NotSupportedMemberTypeException.class)
    public ResponseEntity<?> notSupportedMemberTypeExceptionHandler(final NotSupportedMemberTypeException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponseBody("-004", List.of(exception.getMessage())));
    }

    @ExceptionHandler(NoSuchMemberException.class)
    public ResponseEntity<?> noSuchMemberExceptionHandler(final NoSuchMemberException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponseBody("-010", List.of(exception.getMessage())));
    }
}
