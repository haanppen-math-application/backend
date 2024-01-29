package com.hanpyeon.academyapi.account;


import java.util.List;

public record FieldExceptionResponse(String errorCode, List<?> details) {
}
