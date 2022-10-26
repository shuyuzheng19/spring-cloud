package com.zsy.producer.config.security.filter;

import com.zsy.common.constants.TokenConstants;
import com.zsy.common.enums.ResultMessageStatus;
import com.zsy.common.response.ResultResponse;
import com.zsy.producer.utils.HttpUtils;
import com.zsy.producer.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Base64Utils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 郑书宇
 * @create 2022/10/27 1:42
 * @desc
 */
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final RedisTemplate redisTemplate;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private List<String> noLoginRequiredUris;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getMethod().equals("OPTIONS")||noLoginRequiredUris.contains(request.getRequestURI())){
            filterChain.doFilter(request,response);
            return;
        }
        String token=request.getHeader(TokenConstants.TOKEN_HEADER);
        if(StringUtils.isEmpty(token)){
            HttpUtils.writeJson(response, ResultResponse.fail(ResultMessageStatus.AUTHORIZATION_FAILED));
            return;
        }

        if(!token.startsWith(TokenConstants.TOKEN_PREFIX)){
            HttpUtils.writeJson(response, ResultResponse.fail(ResultMessageStatus.ILLEGAL_TOKEN));
            return;
        }

        String tokenToUsername= TokenUtils.tokenParseToUsername(token.substring(TokenConstants.TOKEN_PREFIX.length()));

        if(StringUtils.isEmpty(tokenToUsername)){
            HttpUtils.writeJson(response, ResultResponse.fail(ResultMessageStatus.AUTHORIZATION_FAILED));
            return;
        }

        Object accessToken = redisTemplate.opsForValue().get(TokenConstants.REDIS_ACCESS_TOKEN_PREFIX + Base64Utils.encodeToString(tokenToUsername.getBytes()));

        if(accessToken==null){
            HttpUtils.writeJson(response, ResultResponse.fail(ResultMessageStatus.EXPIRE_TOKEN));
            return;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(tokenToUsername);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword());

        usernamePasswordAuthenticationToken.setDetails(userDetails);

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        filterChain.doFilter(request,response);

    }

    public void setNoLoginRequiredUri(List<String> noLoginRequiredUris){
        this.noLoginRequiredUris=noLoginRequiredUris;
    }
}
