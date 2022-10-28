package com.zsy.feign.controller;

import com.zsy.common.response.ResultResponse;
import com.zsy.feign.service.FeignClientService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 郑书宇
 * @create 2022/10/28 21:23
 * @desc
 */
@RestController
@RequestMapping("/user")
public class UserController{

    @Resource
    private FeignClientService feignClientService;


    @PostMapping("/login")
    public ResultResponse login(@RequestBody Map<String, Object> map) {
        return feignClientService.login(map);
    }

    @PostMapping("/registered")
    public ResultResponse registeredUser(@RequestBody Map<String, Object> map) {
        return feignClientService.registeredUser(map);
    }

    @PostMapping("/addRole")
    public ResultResponse addRole(@RequestBody Map<String, Object> role) {
        return feignClientService.addRole(role);
    }

    @PostMapping("/addPermission")
    public ResultResponse addPermission(@RequestBody Map<String, Object> permission) {
        return feignClientService.addPermission(permission);
    }

    @GetMapping("/roleToUser/{roleName}/{username}")
    public ResultResponse roleToUser( @PathVariable String roleName, @PathVariable String username) {
        return feignClientService.roleToUser(roleName,username);
    }

    @GetMapping("/perToUser/{perName}/{username}")
    public ResultResponse permissionToUser( @PathVariable("perName") String permissionName, @PathVariable String username) {
        return feignClientService.permissionToUser(permissionName,username);
    }

    @GetMapping("/delete/role/{roleName}")
    public ResultResponse deleteRole( @PathVariable String roleName) {
        return feignClientService.deleteRole(roleName);
    }

    @GetMapping("/delete/permission/{permissionName}")
    public ResultResponse deletePermission( @PathVariable String permissionName) {
        return feignClientService.deletePermission(permissionName);
    }
}
