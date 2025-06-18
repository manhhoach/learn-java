package com.devteria.identity.mapper;

import com.devteria.identity.dto.req.UserCreationRequest;
import com.devteria.identity.dto.res.UserResponse;
import com.devteria.identity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest req);

    void updateUser(@MappingTarget User user, UserCreationRequest req);

    // @Mapping(target = "id", source = "username") target sẽ mang giá trị của source
    UserResponse toUserResponse(User data);
}
