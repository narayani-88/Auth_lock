package com.auth.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.management.relation.Role;


@Entity
@Table(name ="users")
@Data


public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;





}
