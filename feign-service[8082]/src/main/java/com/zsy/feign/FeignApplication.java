package com.zsy.feign;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.zsy.feign.config.MyHystrixCommandHook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @author 郑书宇
 * @create 2022/10/28 21:05
 * @desc
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class FeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignApplication.class,args);
    }

    @Bean
    public HystrixCommandExecutionHook hystrixCommandExecutionHook(){
        MyHystrixCommandHook myHystrixCommandHook=new MyHystrixCommandHook();
        HystrixPlugins.getInstance().registerCommandExecutionHook(myHystrixCommandHook);
        return myHystrixCommandHook;
    }
}
