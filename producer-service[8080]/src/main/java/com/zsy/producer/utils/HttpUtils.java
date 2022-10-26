package com.zsy.producer.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 郑书宇
 * @create 2022/10/27 0:33
 * @desc
 */
public class HttpUtils {


    public static void writeJson(HttpServletResponse response,Object data){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        try {
            new ObjectMapper().writeValue(response.getOutputStream(),data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
