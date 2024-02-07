package com.hanpyeon.academyapi.dto;


import java.util.List;

public record ExceptionResponseBody(String exceptions, List<?> errorFields) {
}
