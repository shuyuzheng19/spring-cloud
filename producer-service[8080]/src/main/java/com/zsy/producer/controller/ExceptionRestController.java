package com.zsy.producer.controller;

import com.zsy.common.exception.GlobalException;
import com.zsy.common.response.ResultResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 郑书宇
 * @create 2022/10/28 19:42
 * @desc
 */
//@RestControllerAdvice
public class ExceptionRestController {

    @ExceptionHandler(value = GlobalException.class)
    public ResultResponse fail1(GlobalException globalException){
        String message=globalException.getMessage();
        return ResultResponse.error(message);
    }
}
