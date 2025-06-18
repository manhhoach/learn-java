package com.devteria.identity.controller;


import com.devteria.identity.dto.req.AuthRequest;
import com.devteria.identity.dto.req.IntrospectRequest;
import com.devteria.identity.dto.res.ApiResponse;
import com.devteria.identity.dto.res.AuthResponse;
import com.devteria.identity.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody AuthRequest req) {
        var auth = authService.authenicate(req);
        var res = new ApiResponse<AuthResponse>();
        res.setResult(auth);
        return res;
    }


    @PostMapping("/introspect")
    public ApiResponse<Boolean> introspect(@RequestBody IntrospectRequest req) {
        var auth = authService.introspect(req);
        var res = new ApiResponse<Boolean>();
        res.setResult(auth);
        return res;
    }
}
