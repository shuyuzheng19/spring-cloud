package com.zsy.producer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zsy.common.response.ResultResponse;
import com.zsy.producer.dto.BookDto;
import com.zsy.producer.service.kafka.KafkaBookService;
import com.zsy.producer.utils.JsonUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * @author 郑书宇
 * @create 2022/10/27 12:24
 * @desc
 */
@RestController
@RequestMapping("/kafka/book")
public class KafkaBookController {

    @Resource
    private KafkaBookService kafkaBookService;

    @GetMapping("/send")
    public ResultResponse sendMessage(String name){
        String uuid= UUID.randomUUID().toString();
        BookDto bookDto = BookDto.builder().id(uuid).date(new Date()).name(name).build();
        kafkaBookService.sendMessage(JsonUtils.objectToJsonString(bookDto));
        return ResultResponse.success();
    }
}
