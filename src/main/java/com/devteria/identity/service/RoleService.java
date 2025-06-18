package com.devteria.identity.service;


import com.devteria.identity.dto.req.RoleRequest;
import com.devteria.identity.dto.res.RoleResponse;
import com.devteria.identity.entity.Role;
import com.devteria.identity.mapper.RoleMapper;
import com.devteria.identity.repository.PermissionRepository;
import com.devteria.identity.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    RoleMapper roleMapper;


    @Transactional
    public RoleResponse create(RoleRequest req) {
        Role data = new Role();
        data.setName(req.getName());
        roleRepository.save(data);
        var permissions = permissionRepository.findAllById(req.getPermissions());
        data.setPermissions(new HashSet<>(permissions));

        var res = new RoleResponse();
        res.setName(data.getName());
        res.setPermissions(data.getPermissions());
        return res;
    }

    public List<RoleResponse> getAll() {
        var per = roleRepository.findAll();
        var data = per.stream().map(e -> {

            var m = new RoleResponse();
            m.setName(e.getName());
            m.setPermissions(e.getPermissions());
            return m;
        }).toList();
        return data;
    }

    public void delete(String name) {
        roleRepository.deleteById(name);
    }
}
