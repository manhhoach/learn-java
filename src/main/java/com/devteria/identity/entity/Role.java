package com.devteria.identity.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Role {
    @Id
    private String name;

    @ManyToMany
    Set<Permission> permissions;

}
