package com.dochero.departmentservice.exception;

public class FolderException extends RuntimeException{
    public FolderException() {
    }

    public FolderException(String message) {
        super(message);
    }

    public FolderException(String message, Throwable cause) {
        super(message, cause);
    }

    public FolderException(Throwable cause) {
        super(cause);
    }
}
