package com.zsy.producer.service;

import com.zsy.producer.dto.UserDto;
import com.zsy.producer.entity.Permission;
import com.zsy.producer.entity.Role;

/**
 * @author 郑书宇
 * @create 2022/10/28 17:46
 * @desc
 */
public interface UserService {

    boolean registerUser(UserDto userDto);

    Role addRole(Role role);

    Permission addPermission(Permission permission);

    void roleToUser(String roleName,String username);

    void permissionToUser(String permissionName,String username);

    void deleteRole(String roleName);

    void deletePermission(String permissionName);
}
