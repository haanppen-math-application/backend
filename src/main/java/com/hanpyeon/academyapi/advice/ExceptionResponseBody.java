package com.hanpyeon.academyapi.advice;


import java.util.List;

public record ExceptionResponseBody(String errorCode, List<?> details) {
}
