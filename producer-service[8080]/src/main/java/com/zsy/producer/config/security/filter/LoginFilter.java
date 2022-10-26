package com.zsy.producer.config.security.filter;

import com.zsy.common.enums.ResultMessageStatus;
import com.zsy.common.response.ResultResponse;
import com.zsy.producer.dto.UserDto;
import com.zsy.producer.repository.UserRepository;
import com.zsy.producer.service.MyUserDetails;
import com.zsy.producer.service.redis.TokenService;
import com.zsy.producer.utils.HttpUtils;
import com.zsy.producer.vo.TokenInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.authentication.AuthenticationManagerFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 郑书宇
 * @create 2022/10/26 21:02
 * @desc
 */
@RequiredArgsConstructor
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.error("异常信息===========>"+failed.getMessage());
        log.error("登陆失败===========>"+request.getParameter("username"));
        HttpUtils.writeJson(response, ResultResponse.fail(ResultMessageStatus.LOGIN_FAIL));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        MyUserDetails details = (MyUserDetails) authResult.getPrincipal();
        UserDto userDto=details.getUserDto();
        TokenInfoResponse tokenInfo = tokenService.createTokenToRedis(userDto);
        HttpUtils.writeJson(response, ResultResponse.success(ResultMessageStatus.LOGIN_SUCCESS,tokenInfo));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if(!request.getMethod().equals("POST")){
            response.setStatus(405);
            return null;
        }
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }
}
