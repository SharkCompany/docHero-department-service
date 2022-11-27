package com.dochero.departmentservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResponse<T> {
    private String status;
    private T data;
    private String message;
    private boolean error;
    private Integer errorCode;


    public BaseResponse(String message, Integer errorCode) {
        this.message = message;
        this.errorCode = errorCode;
        this.error = true;
    }

    public BaseResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public BaseResponse(T data, String message, Integer errorCode) {
        this.data = data;
        this.message = message;
        this.errorCode = errorCode;
        this.error = true;
    }
}
