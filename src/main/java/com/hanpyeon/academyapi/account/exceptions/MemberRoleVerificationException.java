package com.hanpyeon.academyapi.account.exceptions;

public class MemberRoleVerificationException extends RuntimeException{
    public MemberRoleVerificationException(String message) {
        super(message);
    }
}
