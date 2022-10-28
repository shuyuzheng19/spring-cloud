package com.zsy.ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author 郑书宇
 * @create 2022/10/28 18:36
 * @desc
 */
@SpringBootApplication
@EnableEurekaClient
public class RibbonApplication {
    public static void main(String[] args) {
        SpringApplication.run(RibbonApplication.class,args);
    }
}
