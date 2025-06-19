package com.devteria.identity.repository;

import com.devteria.identity.dto.res.*;
import com.devteria.identity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    @Query(value = "Select u.username, r.name as role_name from user u " +
            "join user_roles ur on u.id = ur.user_id join role r on ur.roles_id = r.name where u.username = :username", nativeQuery = true)
    List<Object[]> getDetail(@Param("username") String username);
}
