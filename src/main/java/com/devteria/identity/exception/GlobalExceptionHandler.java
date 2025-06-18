package com.devteria.identity.exception;

import com.devteria.identity.dto.res.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException ex) {
        var res = new ApiResponse<>();
        res.setMessage(ex.getMessage());
        res.setCode(1001);
        return ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException ex) {
        var res = new ApiResponse<>();
        res.setMessage(ex.getErrorCode().getMessage());
        res.setCode(ex.getErrorCode().getCode());
        return ResponseEntity.status(ex.getErrorCode().getStatusCode()).body(res);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidException(MethodArgumentNotValidException ex) {
        var res = new ApiResponse<>();
        ErrorCode e;
        try {
            e = ErrorCode.valueOf(ex.getFieldError().getDefaultMessage());
        } catch (Exception err) {
            e = ErrorCode.KEY_INVALID;
        }
        res.setMessage(e.getMessage());
        res.setCode(e.getCode());
        return ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handleAccessDenied(AccessDeniedException ex) {
        var res = new ApiResponse<>();
        var er = ErrorCode.UNAUTHORIZED;
        res.setCode(er.getCode());
        res.setMessage(er.getMessage());

        return ResponseEntity.status(er.getStatusCode()).body(res);
    }
}
