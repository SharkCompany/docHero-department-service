package com.dochero.departmentservice.dto.response;

public class DepartmentResponse extends BaseResponse{

    public DepartmentResponse(Object data, String message, boolean error, Integer errorCode) {
        super(data, message, error, errorCode);
    }

    public DepartmentResponse(String message, Integer errorCode) {
        super(message, errorCode);
    }

    public DepartmentResponse(Object data, String message) {
        super(data, message);
    }

    public DepartmentResponse(Object data, String message, Integer errorCode) {
        super(data, message, errorCode);
    }
}
