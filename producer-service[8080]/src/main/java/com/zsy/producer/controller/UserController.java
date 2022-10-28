package com.zsy.producer.controller;

import com.zsy.common.enums.ResultMessageStatus;
import com.zsy.common.exception.GlobalException;
import com.zsy.common.response.ResultResponse;
import com.zsy.producer.dto.UserDto;
import com.zsy.producer.entity.Permission;
import com.zsy.producer.entity.Role;
import com.zsy.producer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author 郑书宇
 * @create 2022/10/28 17:41
 * @desc
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/registered")
    public ResultResponse registeredUser(@RequestBody UserDto userDto){
        if(userDto==null) return ResultResponse.fail("请传入用户信息");
        if(StringUtils.isEmpty(userDto.getFirstName())) return ResultResponse.fail("firstName不能为空");
        if(StringUtils.isEmpty(userDto.getLastName())) return ResultResponse.fail("lastName不能为空");
        if(StringUtils.isEmpty(userDto.getUsername())) return ResultResponse.fail("用户名不能为空");
        if(StringUtils.isEmpty(userDto.getPassword())) return ResultResponse.fail("密码不能为空");
        boolean result = userService.registerUser(userDto);
        return result?ResultResponse.success(ResultMessageStatus.REGISTRATION_SUCCESS,userDto.getUsername()+"--->注册成功"):ResultResponse.fail(ResultMessageStatus.REGISTRATION_FAILED);
    }

    @PostMapping("/addRole")
    @PreAuthorize(value = "hasAnyRole('ADMIN') and hasAuthority('INSERT')")
    public ResultResponse addRole(@RequestBody Role role){
        if(role==null) return ResultResponse.fail("请传入角色信息");
        boolean flag= RandomUtils.nextBoolean();
        if(flag){
            throw new GlobalException("模拟程序在这里出现异常了.....");
        }
        role.setId(null);
        if(StringUtils.isEmpty(role.getName())) return ResultResponse.fail("请输入角色名称");
        if(StringUtils.isEmpty(role.getDesc())) return ResultResponse.fail("请输入角色描述信息");
        Role resultRole = userService.addRole(role);
        return resultRole==null?ResultResponse.fail("添加失败"):ResultResponse.success(resultRole);
    }

    @PostMapping("/addPermission")
    @PreAuthorize(value = "hasAnyRole('ADMIN') and hasAuthority('INSERT')")
    public ResultResponse addPermission(@RequestBody Permission permission){
        if(permission==null) return ResultResponse.fail("请传入权限信息");
        permission.setId(null);
        if(StringUtils.isEmpty(permission.getName())) return ResultResponse.fail("请输入角色名称");
        if(StringUtils.isEmpty(permission.getDesc())) return ResultResponse.fail("请输入角色描述信息");
        Permission resultPermission = userService.addPermission(permission);
        return resultPermission==null?ResultResponse.fail("添加失败"):ResultResponse.success(resultPermission);
    }

    @GetMapping("/roleToUser/{roleName}/{username}")
    @PreAuthorize(value = "hasAnyRole('ADMIN') and hasAuthority('UPDATE')")
    public ResultResponse roleToUser(@PathVariable("roleName") String roleName,@PathVariable("username") String username){
        userService.roleToUser(roleName,username);
        return ResultResponse.success();
    }

    @GetMapping("/perToUser/{perName}/{username}")
    @PreAuthorize(value = "hasAnyRole('ADMIN') and hasAuthority('UPDATE')")
    public ResultResponse permissionToUser(@PathVariable("perName") String permissionName,@PathVariable("username") String username){
        userService.permissionToUser(permissionName,username);
        return ResultResponse.success();
    }

    @GetMapping("/delete/role/{roleName}")
    @PreAuthorize(value = "hasAnyRole('ADMIN') and hasAuthority('UPDATE')")
    public ResultResponse deleteRole(@PathVariable("roleName") String roleName){
        userService.deleteRole(roleName);
        return ResultResponse.success();
    }

    @GetMapping("/delete/permission/{permissionName}")
    @PreAuthorize(value = "hasAnyRole('ADMIN') and hasAuthority('UPDATE')")
    public ResultResponse deletePermission(@PathVariable("permissionName") String permissionName){
        userService.deletePermission(permissionName);
        return ResultResponse.success();
    }


}
