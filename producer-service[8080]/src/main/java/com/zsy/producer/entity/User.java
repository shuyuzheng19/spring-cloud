package com.zsy.producer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 郑书宇
 * @create 2022/10/26 19:12
 * @desc
 */
@Data @Entity @Table(name="user")
@NoArgsConstructor
@AllArgsConstructor
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="username",nullable = false,length = 16)
    private String username;

    @Column(name="first_name",nullable = false,length = 1)
    private String firstName;

    @Column(name="last_name",nullable = false,length = 4)
    private String lastName;

    @Column(name="password",nullable = false,length = 16)
    private String password;

    @OneToOne(fetch = FetchType.EAGER,targetEntity = Role.class)
    private Role role;

    @ManyToMany(fetch = FetchType.EAGER,targetEntity = Permission.class)
    private Set<Permission> permissions=new HashSet<>();
}
