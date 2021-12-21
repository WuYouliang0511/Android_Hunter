package com.lotogram.ihunter.network.http;

public class BaseResponse {

    private String status;
    private int code;
    private String message;

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isOk() {
        return "ok".equals(status);
    }
}