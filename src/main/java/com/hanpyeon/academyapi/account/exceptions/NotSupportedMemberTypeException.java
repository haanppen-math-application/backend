package com.hanpyeon.academyapi.account.exceptions;

public class NotSupportedMemberTypeException extends RuntimeException {
    public NotSupportedMemberTypeException(String message) {
        super(message);
    }
}
