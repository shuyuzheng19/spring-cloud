package com.zsy.producer.service.kafka;

import com.zsy.common.constants.KafkaConstants;
import com.zsy.producer.dto.BookDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @author 郑书宇
 * @create 2022/10/27 12:19
 * @desc
 */
@Service
@Slf4j
public class KafkaBookService {

    @Resource
    private KafkaTemplate kafkaTemplate;


    public void sendMessage(String book){
        kafkaTemplate.send(KafkaConstants.BOOK_TOPIC_ID,book);
        log.info("发送消息-->{}",book);
    }


}
