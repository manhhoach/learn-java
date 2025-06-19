package com.devteria.identity.service;


import com.devteria.identity.dto.req.UserCreationRequest;
import com.devteria.identity.dto.res.UserInfoResponse;
import com.devteria.identity.dto.res.UserResponse;
import com.devteria.identity.entity.User;
import com.devteria.identity.enums.RoleEnum;
import com.devteria.identity.exception.AppException;
import com.devteria.identity.exception.ErrorCode;
import com.devteria.identity.mapper.UserMapper;
import com.devteria.identity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest req) {

        if (userRepository.existsByUsername(req.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(req);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(RoleEnum.USER.name());
        //  user.setRoles(roles);

        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(e -> userMapper.toUserResponse(e)).toList();
    }

    public UserResponse getById(String id) {
        var user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    public UserResponse update(UserCreationRequest req, String id) {
        var user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(user, req);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }


    public void delete(String id) {
        userRepository.deleteById(id);
    }


    public UserInfoResponse getMyInfo() {
        var ctx = SecurityContextHolder.getContext();
        var authentication = ctx.getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        String username = authentication.getName();
        List<Object[]> results = userRepository.getDetail(username);

        if (results.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        Set<String> roles = results.stream()
                .map(row -> (String) row[1]) // role_name
                .collect(Collectors.toSet());

        var res = new UserInfoResponse();
        res.setUsername(username);
        res.setRoles(roles);
        return res;
    }

}
