package com.zsy.producer.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 郑书宇
 * @create 2022/10/27 0:32
 * @desc
 */
@Data @AllArgsConstructor
public class TokenInfoResponse {

    private String accessToken;

    private String refreshToken;

}
