package com.hanpyeon.academyapi.board.exception;

public class InvalidUploadFileException extends RuntimeException{
    public InvalidUploadFileException(String message) {
        super(message);
    }
}
