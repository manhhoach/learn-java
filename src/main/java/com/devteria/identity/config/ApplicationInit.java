package com.devteria.identity.config;


import com.devteria.identity.entity.User;
import com.devteria.identity.enums.RoleEnum;
import com.devteria.identity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.HashSet;

@Configuration
public class ApplicationInit {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if(!userRepository.existsByUsername("admin")){
                User user = new User();
                user.setPassword(passwordEncoder.encode("12345678"));
                user.setUsername("admin");
                user.setDob(LocalDate.now());

                var hs = new HashSet<String>();
                hs.add(RoleEnum.ADMIN.name());

                user.setRoles(hs);
                userRepository.save(user);
            }
        };
    }
}
