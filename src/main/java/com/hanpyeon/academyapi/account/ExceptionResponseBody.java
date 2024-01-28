package com.hanpyeon.academyapi.account;


import java.util.List;

public record ExceptionResponseBody(String exceptions, List<?> errorFields) {
}
