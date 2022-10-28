package com.zsy.producer.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author 郑书宇
 * @create 2022/10/28 16:09
 * @desc
 */
public class JsonUtils {

    public static String objectToJsonString(Object object){
        try {
            String jsonStr = new ObjectMapper().writeValueAsString(object);
            return jsonStr;
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
