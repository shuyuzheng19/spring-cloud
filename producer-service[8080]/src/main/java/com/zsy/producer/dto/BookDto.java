package com.zsy.producer.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 郑书宇
 * @create 2022/10/27 12:20
 * @desc
 */
@Data @Builder
public class BookDto implements Serializable {
    private String id;

    private String name;

    private Date date;
}
