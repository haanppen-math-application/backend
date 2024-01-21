package com.hanpyeon.academyapi.exception;

import com.hanpyeon.academyapi.dto.ExceptionResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ArgumentExceptionAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> responseProperCode(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getFieldErrors().stream()
                .map(o -> o.getField())
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(new ExceptionResponseBody("-001", errors));
    }
}
