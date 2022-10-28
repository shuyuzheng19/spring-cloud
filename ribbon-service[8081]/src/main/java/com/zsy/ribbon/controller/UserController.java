package com.zsy.ribbon.controller;

import com.zsy.common.constants.TokenConstants;
import com.zsy.common.response.ResultResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 郑书宇
 * @create 2022/10/28 18:31
 * @desc
 */

@RestController
@RequestMapping("/user")
public class UserController {

    private static final String URI="http://"+"PRODUCER-SERVICE";

    @Resource
    private RestTemplate restTemplate;

    @PostMapping("/registered")
    public ResultResponse registeredUser(@RequestBody Map<String,Object>map){
        return restTemplate.postForEntity(URI + "/user/registered", map, ResultResponse.class).getBody();
    }

    @PostMapping("/login")
    public ResultResponse login(HttpServletRequest request){
        MultiValueMap<String,Object> map=new LinkedMultiValueMap<>();
        map.add("username",request.getParameter("username"));
        map.add("password",request.getParameter("password"));
        HttpEntity httpEntity=defaultHttpEntity(null,MediaType.MULTIPART_FORM_DATA,map);
        return restTemplate.exchange(URI+"/user/login", HttpMethod.POST,httpEntity,ResultResponse.class).getBody();
    }

    @PostMapping("/addRole")
    public ResultResponse addRole(@RequestHeader(TokenConstants.TOKEN_HEADER) String token, @RequestBody Map<String,Object> role){
        HttpEntity httpEntity=defaultHttpEntity(token,MediaType.APPLICATION_JSON_UTF8,role);
        return restTemplate.exchange(URI+"/user/addRole",HttpMethod.POST,httpEntity,ResultResponse.class).getBody();
    }

    private HttpEntity defaultHttpEntity(String token,MediaType mediaType,Object data){
        HttpHeaders httpHeaders=new HttpHeaders();
        if(!StringUtils.isEmpty(token)){
            httpHeaders.add(TokenConstants.TOKEN_HEADER,token);
        }
        httpHeaders.setContentType(mediaType);
        HttpEntity httpEntity=new HttpEntity(data,httpHeaders);
        return httpEntity;
    }

    @PostMapping("/addPermission")
    public ResultResponse addPermission(@RequestHeader(value = TokenConstants.TOKEN_HEADER) String token,@RequestBody  Map<String,Object>  permission){
        HttpEntity httpEntity=defaultHttpEntity(token, MediaType.APPLICATION_JSON_UTF8,permission);
        return restTemplate.exchange(URI+"/user/addRole",HttpMethod.POST,httpEntity,ResultResponse.class).getBody();
    }


    @GetMapping("/roleToUser/{roleName}/{username}")
    public ResultResponse roleToUser(@RequestHeader(value = TokenConstants.TOKEN_HEADER) String token,@PathVariable("roleName") String roleName,@PathVariable("username") String username){
        HttpEntity httpEntity=defaultHttpEntity(token,null,null);
        return restTemplate.exchange(URI+"/user/roleToUser/"+roleName+"/"+username,HttpMethod.GET,httpEntity,ResultResponse.class).getBody();
    }

    @GetMapping("/perToUser/{perName}/{username}")
    public ResultResponse permissionToUser(@RequestHeader(value = TokenConstants.TOKEN_HEADER) String token,@PathVariable("perName") String permissionName,@PathVariable("username") String username){
        HttpEntity httpEntity=defaultHttpEntity(token,null,null);
        return restTemplate.exchange(URI+"/user/perToUser/"+permissionName+"/"+username,HttpMethod.GET,httpEntity,ResultResponse.class).getBody();
    }

    @GetMapping("/delete/role/{roleName}")
    public ResultResponse deleteRole(@RequestHeader(value = TokenConstants.TOKEN_HEADER) String token,@PathVariable("roleName") String roleName){
        HttpEntity httpEntity=defaultHttpEntity(token,null,null);
        return restTemplate.exchange(URI+"/user/delete/role/"+roleName,HttpMethod.GET,httpEntity,ResultResponse.class).getBody();
    }

    @GetMapping("/delete/permission/{permissionName}")
    public ResultResponse deletePermission(@RequestHeader(value = TokenConstants.TOKEN_HEADER) String token,@PathVariable("permissionName") String permissionName){
        HttpEntity httpEntity=defaultHttpEntity(token,null,null);
        return restTemplate.exchange(URI+"/user/delete/permission/"+permissionName,HttpMethod.GET,httpEntity,ResultResponse.class).getBody();
    }

}
