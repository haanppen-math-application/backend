package com.hanpyeon.academyapi;


import java.util.List;

public record ExceptionResponseBody(String errorCode, List<?> details) {
}
