package com.zsy.feign.service;

import com.zsy.common.constants.TokenConstants;
import com.zsy.common.response.ResultResponse;
import com.zsy.feign.config.FeignConfiguration;
import com.zsy.feign.config.MyFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 郑书宇
 * @create 2022/10/28 21:06
 * @desc
 */
@FeignClient(name = "PRODUCER-SERVICE",configuration = FeignConfiguration.class,path = "/user",fallbackFactory = MyFallBackFactory.class)
public interface FeignClientService {

    @PostMapping("/login")
    ResultResponse login(@RequestBody Map<String,Object> map);

    @PostMapping("/registered")
    ResultResponse registeredUser(@RequestBody Map<String,Object>map);

    @PostMapping("/addRole")
    ResultResponse addRole(@RequestHeader(TokenConstants.TOKEN_HEADER) String token, @RequestBody Map<String,Object> role);

    @PostMapping("/addPermission")
    ResultResponse addPermission(@RequestHeader(value = TokenConstants.TOKEN_HEADER) String token,@RequestBody  Map<String,Object>  permission);

    @GetMapping("/roleToUser/{roleName}/{username}")
    ResultResponse roleToUser(@RequestHeader(value = TokenConstants.TOKEN_HEADER) String token, @PathVariable("roleName") String roleName, @PathVariable("username") String username);

    @GetMapping("/perToUser/{perName}/{username}")
    ResultResponse permissionToUser(@RequestHeader(value = TokenConstants.TOKEN_HEADER) String token,@PathVariable("perName") String permissionName,@PathVariable("username") String username);

    @GetMapping("/delete/role/{roleName}")
    ResultResponse deleteRole(@RequestHeader(value = TokenConstants.TOKEN_HEADER) String token,@PathVariable("roleName") String roleName);

    @GetMapping("/delete/permission/{permissionName}")
    ResultResponse deletePermission(@RequestHeader(value = TokenConstants.TOKEN_HEADER) String token,@PathVariable("permissionName") String permissionName);
}
