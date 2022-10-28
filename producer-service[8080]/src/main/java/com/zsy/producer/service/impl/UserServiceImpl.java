package com.zsy.producer.service.impl;

import com.zsy.common.exception.GlobalException;
import com.zsy.producer.dto.UserDto;
import com.zsy.producer.entity.Permission;
import com.zsy.producer.entity.Role;
import com.zsy.producer.entity.User;
import com.zsy.producer.repository.PermissionRepository;
import com.zsy.producer.repository.RoleRepository;
import com.zsy.producer.repository.UserRepository;
import com.zsy.producer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 郑书宇
 * @create 2022/10/28 17:47
 * @desc
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Role DEFAULT_ROLE=new Role(1);

    private final UserRepository userRepository;

    private final PermissionRepository permissionRepository;

    private final RoleRepository roleRepository;

    @Override
    public boolean registerUser(UserDto userDto) {
        boolean present = userRepository.findByUsername(userDto.getUsername()).isPresent();
        if(present){
            return false;
        }
        User user=new User();
        BeanUtils.copyProperties(userDto,user);
        user.setRole(DEFAULT_ROLE);
        user.setPermissions(new HashSet<>());
        User resultUser = userRepository.save(user);
        return resultUser==null?false:true;
    }

    @Override
    public Role addRole(Role role) {
        boolean present = roleRepository.findByName(role.getName()).isPresent();
        if(present){
            return null;
        }
        Role resultRole = roleRepository.save(role);
        return resultRole;
    }

    @Override
    public Permission addPermission(Permission permission) {
        boolean present = permissionRepository.findByName(permission.getName()).isPresent();
        if(present){
            return null;
        }
        Permission resultPermission = permissionRepository.save(permission);
        return resultPermission;
    }

    @Override
    public void roleToUser(String roleName, String username) {
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new GlobalException("该角色不存在"));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new GlobalException("该用户不存在"));
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public void permissionToUser(String permissionName, String username) {
        Permission permission = permissionRepository.findByName(permissionName).orElseThrow(() -> new GlobalException("该权限不存在"));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new GlobalException("该用户不存在"));
        Set<Permission> permissions = user.getPermissions();
        permissions.add(permission);
        user.setPermissions(permissions);
        userRepository.save(user);
    }

    @Override
    public void deleteRole(String roleName) {
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new GlobalException("该角色不存在"));
        roleRepository.delete(role);
    }

    @Override
    public void deletePermission(String permissionName) {
        Permission permission = permissionRepository.findByName(permissionName).orElseThrow(() -> new GlobalException("该权限不存在"));
        permissionRepository.delete(permission);
    }
}
