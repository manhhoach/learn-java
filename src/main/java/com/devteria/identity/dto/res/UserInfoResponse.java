package com.devteria.identity.dto.res;

import lombok.Data;

import java.util.Set;

@Data
public class UserInfoResponse {

    private String username;
    private Set<String> roles;
}
