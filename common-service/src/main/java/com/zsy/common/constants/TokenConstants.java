package com.zsy.common.constants;

/**
 * @author 郑书宇
 * @create 2022/10/26 21:19
 * @desc
 */
public interface TokenConstants {
    //token的前缀
    String TOKEN_PREFIX="Bearer ";

    //token请求头的名称
    String TOKEN_HEADER="Authorization";

    //token加密密钥
    String TOKEN_ENCRYPT_STR="z@2528959216!~";

    //用户token失效时间 单位:分钟
    long ACCESS_TOKEN_EXPIRE_TIME=20;

    //刷新token失效时间 单位:分钟
    long REFRESH_TOKEN_EXPIRE_TIME=30;

    //redis存取 用户token的前缀
    String REDIS_ACCESS_TOKEN_PREFIX="access_token:";

    //redis存取 刷新token的前缀
    String REDIS_REFRESH_TOKEN_PREFIX="refresh_token:";
}
