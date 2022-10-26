package com.zsy.producer.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zsy.common.constants.TokenConstants;
import com.zsy.producer.dto.UserDto;
import com.zsy.producer.entity.User;

import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author 郑书宇
 * @create 2022/10/26 21:23
 * @desc
 */
public class TokenUtils {
    private static final Algorithm DEFAULT_ALGORITHM=Algorithm.HMAC256(TokenConstants.TOKEN_ENCRYPT_STR);

    public static String createAccessToken(UserDto user){
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuer(user.getUsername())
                .withClaim("roles",user.getRole().getName())
                .withClaim("permissions",user.getPermissions().stream().map(permission->permission.getName()).collect(Collectors.toList()))
                .withExpiresAt(new Date(System.currentTimeMillis()+TokenConstants.ACCESS_TOKEN_EXPIRE_TIME*60*1000))
                .sign(DEFAULT_ALGORITHM);
    }

    public static String createRefreshToken(String username){
        return JWT.create()
                .withSubject(username)
                .withIssuer(username)
                .withExpiresAt(new Date(System.currentTimeMillis()+TokenConstants.REFRESH_TOKEN_EXPIRE_TIME*60*1000))
                .sign(DEFAULT_ALGORITHM);
    }


    public static String tokenParseToUsername(String token){
        JWTVerifier jwtVerifier=JWT.require(DEFAULT_ALGORITHM).build();
        try{
            DecodedJWT verify = jwtVerifier.verify(token);
            return verify.getSubject();
        }catch (Exception exception){
            return null;
        }
    }

}
