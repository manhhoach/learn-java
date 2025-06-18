package com.devteria.identity.mapper;

import com.devteria.identity.dto.req.RoleRequest;
import com.devteria.identity.dto.res.RoleResponse;
import com.devteria.identity.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    //Role toRole(RoleRequest req);

    //RoleResponse toRoleResponse(Role role);
}
