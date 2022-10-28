package com.zsy.feign.config;

import com.zsy.common.constants.TokenConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 郑书宇
 * @create 2022/10/28 21:15
 * @desc
 */
public class FeignConfiguration implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request=getCurrentHttpServletRequest();
        if(request==null){
            return;
        }
        String tokenHeader=request.getHeader(TokenConstants.TOKEN_HEADER);
        if(!StringUtils.isEmpty(tokenHeader)){
            requestTemplate.header(TokenConstants.TOKEN_HEADER,tokenHeader);
        }
    }


    private HttpServletRequest getCurrentHttpServletRequest(){
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }



}
