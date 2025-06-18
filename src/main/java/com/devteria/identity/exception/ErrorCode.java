package com.devteria.identity.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


@Getter
public enum ErrorCode {


    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1002, "User not found", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(1001, "Username invalid", HttpStatus.BAD_REQUEST),
    KEY_INVALID(1003, "Key invalid", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(1004, "UNAUTHENTICATED", HttpStatus.UNAUTHORIZED),
    SERVER_ERROR(9999, "Server error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED(1005, "UNAUTHORIZED", HttpStatus.FORBIDDEN),
    PASSWORD_INVALID(1001, "Password invalid", HttpStatus.BAD_REQUEST);


    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = httpStatusCode;
    }
}
