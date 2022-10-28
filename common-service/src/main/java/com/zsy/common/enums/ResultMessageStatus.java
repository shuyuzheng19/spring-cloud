package com.zsy.common.enums;

/**
 * @author 郑书宇
 * @create 2022/10/27 1:05
 * @desc
 */
public enum  ResultMessageStatus {
    /*=====================成功返回=====================*/
    DEFAULT_SUCCESS(200,"处理成功"),
    LOGIN_SUCCESS(201,"登录成功"),
    REGISTRATION_SUCCESS(201,"注册成功"),

    /*=====================失败返回=====================*/
    DEFAULT_FAIL(10000,"处理失败"),
    LOGIN_FAIL(10001,"登陆失败"),
    AUTHORIZATION_FAILED(1002,"授权失败"),
    ILLEGAL_TOKEN(1003,"不合法的token"),
    EXPIRE_TOKEN(1004,"不合法的token"),
    REGISTRATION_FAILED(1005,"注册失败"),


    /*=====================错误返回=====================*/
    DEFAULT_ERROR(500,"服务器异常"),
    ;

    private int code;

    private String message;

    ResultMessageStatus(int code,String message){
        this.code=code;
        this.message=message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
