package com.devteria.identity.service;


import com.devteria.identity.dto.AuthRequest;
import com.devteria.identity.exception.AppException;
import com.devteria.identity.exception.ErrorCode;
import com.devteria.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public boolean authenicate(AuthRequest req){
        var user = userRepository.findByUsername(req.getUsername()).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
        var pw = new BCryptPasswordEncoder(10);
        return pw.matches(req.getPassword(), user.getPassword());
    }
}
