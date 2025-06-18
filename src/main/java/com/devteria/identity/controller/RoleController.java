package com.devteria.identity.controller;


import com.devteria.identity.dto.req.RoleRequest;
import com.devteria.identity.dto.res.ApiResponse;
import com.devteria.identity.dto.res.RoleResponse;
import com.devteria.identity.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    RoleService roleService;


    @GetMapping
    public ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder().result(roleService.getAll()).build();
    }

    @PostMapping
    public ApiResponse<RoleResponse> create(@RequestBody RoleRequest req) {
        return ApiResponse.<RoleResponse>builder().result(roleService.create(req)).build();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        roleService.delete(id);
    }
}
