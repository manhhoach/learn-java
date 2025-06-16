package com.devteria.identity.exception;

public enum ErrorCode {


    USER_EXISTED(1001,"User existed"),
    USER_NOT_FOUND(1002, "User not found"),
    USERNAME_INVALID(1001,"Username invalid"),
    KEY_INVALID(1003, "Key invalid"),
    PASSWORD_INVALID(1001,"Password invalid");



    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
