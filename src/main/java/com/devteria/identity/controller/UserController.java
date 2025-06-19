package com.devteria.identity.controller;


import com.devteria.identity.dto.req.UserCreationRequest;
import com.devteria.identity.dto.res.ApiResponse;
import com.devteria.identity.dto.res.UserInfoResponse;
import com.devteria.identity.dto.res.UserResponse;
import com.devteria.identity.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest data) {
        var user = userService.createUser(data);
        ApiResponse<UserResponse> res = new ApiResponse<>();
        res.setResult(user);
        return res;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers() {
        ApiResponse<List<UserResponse>> res = new ApiResponse<>();
        var users = userService.getUsers();
        res.setResult(users);
        return res;
    }

    @GetMapping("{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable String id) {
        var user = userService.getById(id);
        ApiResponse<UserResponse> res = new ApiResponse<>();
        res.setResult(user);
        return res;
    }


    @PutMapping("{id}")
    public ApiResponse<UserResponse> updateUser(@RequestBody UserCreationRequest req, @PathVariable String id) {
        var user = userService.update(req, id);
        ApiResponse<UserResponse> res = new ApiResponse<>();
        res.setResult(user);
        return res;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        userService.delete(id);
    }

    @GetMapping("/my-info")
    public ApiResponse<UserInfoResponse> getMyInfo() {
        var a = new ApiResponse<UserInfoResponse>();
        a.setResult(userService.getMyInfo());
        return a;
    }
}
