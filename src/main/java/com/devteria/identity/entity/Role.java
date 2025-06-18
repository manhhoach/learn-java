package com.devteria.identity.entity;


import jakarta.persistence.*;
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
