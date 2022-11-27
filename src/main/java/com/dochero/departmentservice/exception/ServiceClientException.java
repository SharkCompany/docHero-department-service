package com.dochero.departmentservice.exception;

public class ServiceClientException extends RuntimeException{
    public ServiceClientException() {
    }

    public ServiceClientException(String message) {
        super(message);
    }

    public ServiceClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceClientException(Throwable cause) {
        super(cause);
    }
}
