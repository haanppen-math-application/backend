package com.hanpyeon.academyapi.exception;

import com.hanpyeon.academyapi.dto.ExceptionResponseBody;
import com.hanpyeon.academyapi.exceptions.AlreadyRegisteredException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @ExceptionHandler(AlreadyRegisteredException.class)
    public ResponseEntity<?> responseProperCode1(AlreadyRegisteredException exception) {
        Map<String, String> body = new HashMap<>();
        body.put("errorCode", "-002");
        return ResponseEntity.badRequest().body(new ExceptionResponseBody("-002", List.of(exception.getMessage())));
    }

}
