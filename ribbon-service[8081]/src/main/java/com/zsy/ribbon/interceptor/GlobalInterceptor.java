package com.zsy.ribbon.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zsy.common.constants.TokenConstants;
import com.zsy.common.enums.ResultMessageStatus;
import com.zsy.common.response.ResultResponse;
import org.aopalliance.intercept.Interceptor;
import org.apache.commons.lang.StringUtils;
import org.springframework.cglib.transform.impl.InterceptFieldFilter;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 郑书宇
 * @create 2022/10/28 20:39
 * @desc
 */
public class GlobalInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token=request.getHeader(TokenConstants.TOKEN_HEADER);
        if(StringUtils.isEmpty(token)){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
            try {
                new ObjectMapper().writeValue(response.getOutputStream(), ResultResponse.fail(ResultMessageStatus.AUTHORIZATION_FAILED));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }
}
