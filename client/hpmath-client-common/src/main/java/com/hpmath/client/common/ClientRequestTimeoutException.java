package com.hpmath.client.common;

public class ClientRequestTimeoutException extends ClientException {
    public ClientRequestTimeoutException(String message) {
        super(message);
    }

    public ClientRequestTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
