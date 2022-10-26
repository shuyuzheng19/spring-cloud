package com.zsy.producer.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author 郑书宇
 * @create 2022/10/26 19:05
 * @desc
 */
@Configuration
public class RedisConfiguration {

    @Bean
    @Primary
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory connectionFactory,Jackson2JsonRedisSerializer jackson2ObjectMapperFactoryBean){
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2ObjectMapperFactoryBean);
        redisTemplate.setValueSerializer(jackson2ObjectMapperFactoryBean);
        return redisTemplate;
    }


    @Bean
    public Jackson2JsonRedisSerializer jackson2ObjectMapperFactoryBean(){
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer=new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }
}
