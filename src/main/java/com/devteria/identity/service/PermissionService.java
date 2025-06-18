package com.devteria.identity.service;


import com.devteria.identity.dto.req.PermissionRequest;
import com.devteria.identity.dto.res.PermissionResponse;
import com.devteria.identity.entity.Permission;
import com.devteria.identity.mapper.PermissionMapper;
import com.devteria.identity.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest req) {
        Permission per = permissionMapper.toPermission(req);
        permissionRepository.save(per);
        return permissionMapper.toPermissionResponse(per);
    }

    public List<PermissionResponse> getAll() {
        var per = permissionRepository.findAll();
        var data = per.stream().map(e -> permissionMapper.toPermissionResponse(e)).toList();
        return data;
    }

    public void delete(String name) {
        permissionRepository.deleteById(name);
    }
}
