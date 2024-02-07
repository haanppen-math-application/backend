package com.hanpyeon.academyapi.account.exceptions;

public class NoSuchMemberException extends RuntimeException {
    public NoSuchMemberException(String message) {
        super(message);
    }
}
