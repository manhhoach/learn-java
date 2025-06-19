package com.devteria.identity.config;

import com.devteria.identity.dto.res.ApiResponse;
import com.devteria.identity.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode e = ErrorCode.UNAUTHENTICATED;
        response.setStatus(e.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ApiResponse a = new ApiResponse();
        a.setCode(e.getCode());
        a.setMessage(e.getMessage());
        String json = new ObjectMapper().writeValueAsString(a);
        response.getWriter().write(json);
        response.flushBuffer();

    }
}
