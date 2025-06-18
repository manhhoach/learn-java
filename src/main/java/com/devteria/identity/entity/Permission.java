package com.devteria.identity.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Permission {
    @Id
    private String name;

}
