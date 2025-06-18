package com.devteria.identity.service;


import com.devteria.identity.dto.req.AuthRequest;
import com.devteria.identity.dto.req.IntrospectRequest;
import com.devteria.identity.dto.res.AuthResponse;
import com.devteria.identity.entity.Role;
import com.devteria.identity.entity.User;
import com.devteria.identity.exception.AppException;
import com.devteria.identity.exception.ErrorCode;
import com.devteria.identity.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    public AuthResponse authenicate(AuthRequest req) {
        var user = userRepository.findByUsername(req.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        var pw = new BCryptPasswordEncoder(10);
        var res = new AuthResponse();
        if (pw.matches(req.getPassword(), user.getPassword())) {
            var token = generateToken(user);
            res.setToken(token);
        } else {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return res;
    }

    public Boolean introspect(IntrospectRequest req) {
        var token = req.getToken();
        try {
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            try {
                SignedJWT sign = SignedJWT.parse(token);

                Date expireTime = sign.getJWTClaimsSet().getExpirationTime();


                return sign.verify(verifier) && expireTime.after(new Date());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("link.url")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(claims.toJSONObject());
        JWSObject jwt = new JWSObject(header, payload);
        try {
            jwt.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwt.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        Set<Role> roles = user.getRoles();
        if (roles == null || roles.isEmpty()) {
            return "";
        }
        return String.join(" ", roles.stream().map(e -> e.getName()).toList());
    }
}
