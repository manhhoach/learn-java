package com.devteria.identity.config;


import com.nimbusds.jose.JWSAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String[] PUBLIC_ENPOINTS={"/users","/auth/**"};

    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(auth->
                auth
                        .requestMatchers(HttpMethod.POST, PUBLIC_ENPOINTS).permitAll()
                        .anyRequest().authenticated()
        );

        http.oauth2ResourceServer(x->x.jwt(j->j.decoder(jwtDecoder())));
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder(){
        SecretKeySpec s = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS256");
        return NimbusJwtDecoder.withSecretKey(s).macAlgorithm(MacAlgorithm.HS256).build();

    }
}
