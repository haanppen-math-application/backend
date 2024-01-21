package com.hanpyeon.academyapi.exceptions;

public class AlreadyRegisteredException extends RuntimeException{
    public AlreadyRegisteredException(String message) {
        super(message);
    }
}
