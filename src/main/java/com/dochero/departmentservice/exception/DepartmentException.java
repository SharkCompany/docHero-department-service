package com.dochero.departmentservice.exception;

public class DepartmentException extends RuntimeException{
    public DepartmentException() {
    }

    public DepartmentException(String message) {
        super(message);
    }

    public DepartmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepartmentException(Throwable cause) {
        super(cause);
    }
}
