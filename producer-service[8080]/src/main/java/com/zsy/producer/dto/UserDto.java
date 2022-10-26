package com.zsy.producer.dto;

import com.zsy.producer.entity.Permission;
import com.zsy.producer.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * @author 郑书宇
 * @create 2022/10/26 20:42
 * @desc
 */
@Data @Builder
public class UserDto {

    private String username;

    private String password;

    private Role role;

    private Set<Permission> permissions;
}
