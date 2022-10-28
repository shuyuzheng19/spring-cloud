package com.zsy.feign.config;

import com.netflix.hystrix.HystrixInvokable;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * @author 郑书宇
 * @create 2022/6/24 12:10
 * @desc
 */
public class MyHystrixCommandHook extends HystrixCommandExecutionHook {

    @Override
    public <T> void onStart(HystrixInvokable<T> commandInstance) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(requestAttributes,true);
        super.onStart(commandInstance);
    }
}
