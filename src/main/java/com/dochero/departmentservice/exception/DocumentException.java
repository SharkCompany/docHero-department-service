package com.dochero.departmentservice.exception;

public class DocumentException extends RuntimeException {

    public DocumentException() {
    }

    public DocumentException(String message) {
        super(message);
    }

    public DocumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentException(Throwable cause) {
        super(cause);
    }
}
