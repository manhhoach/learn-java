package com.devteria.identity.mapper;


import com.devteria.identity.dto.req.PermissionRequest;
import com.devteria.identity.dto.res.PermissionResponse;
import com.devteria.identity.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest req);
    PermissionResponse toPermissionResponse(Permission req);
}
