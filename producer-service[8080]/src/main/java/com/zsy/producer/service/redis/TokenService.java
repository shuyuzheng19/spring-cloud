package com.zsy.producer.service.redis;

import com.zsy.common.constants.TokenConstants;
import com.zsy.producer.dto.UserDto;
import com.zsy.producer.entity.User;
import com.zsy.producer.utils.TokenUtils;
import com.zsy.producer.vo.TokenInfoResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author 郑书宇
 * @create 2022/10/26 21:22
 * @desc
 */
public class TokenService {

    @Resource
    private RedisTemplate redisTemplate;

    public TokenInfoResponse createTokenToRedis(User user){
        String accessToken= TokenUtils.createAccessToken(user);
        String refreshToken=TokenUtils.createRefreshToken(user.getUsername());
        String encodingUsername= Base64Utils.encodeToString(user.getUsername().getBytes());
        redisTemplate.opsForValue().set(TokenConstants.REDIS_ACCESS_TOKEN_PREFIX+encodingUsername,accessToken,TokenConstants.ACCESS_TOKEN_EXPIRE_TIME, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(TokenConstants.REDIS_REFRESH_TOKEN_PREFIX+encodingUsername,refreshToken,TokenConstants.REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MINUTES);
        return new TokenInfoResponse(accessToken,refreshToken);
    }

}
