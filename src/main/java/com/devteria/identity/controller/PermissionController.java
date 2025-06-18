package com.devteria.identity.controller;


import com.devteria.identity.dto.req.PermissionRequest;
import com.devteria.identity.dto.res.ApiResponse;
import com.devteria.identity.dto.res.PermissionResponse;
import com.devteria.identity.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController {
    @Autowired
    PermissionService permissionService;


    @GetMapping
    public ApiResponse<List<PermissionResponse>> getAll(){
        return ApiResponse.<List<PermissionResponse>>builder().result(permissionService.getAll()).build();
    }

    @PostMapping
    public ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest req){
        return ApiResponse.<PermissionResponse>builder().result(permissionService.create(req)).build();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        permissionService.delete(id);
    }
}
