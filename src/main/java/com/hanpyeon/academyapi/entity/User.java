package com.hanpyeon.academyapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import javax.management.relation.Role;

@Entity
@Table(name = "user")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String userName;
    @NotNull
    private String phoneNumber;
    private String password;
    private Integer grade;
    @NotNull
    private Role role;
}
