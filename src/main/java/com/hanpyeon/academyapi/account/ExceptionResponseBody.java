package com.hanpyeon.academyapi.account;


import java.util.List;

public record ExceptionResponseBody(String errorCode, List<?> details) {
}
