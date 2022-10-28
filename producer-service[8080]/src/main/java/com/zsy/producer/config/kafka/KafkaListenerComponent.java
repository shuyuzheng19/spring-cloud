package com.zsy.producer.config.kafka;

import com.zsy.common.constants.KafkaConstants;
import com.zsy.producer.dto.BookDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author 郑书宇
 * @create 2022/10/27 12:14
 * @desc
 */
@Component
@Slf4j
public class KafkaListenerComponent {

    @KafkaListener(topics = KafkaConstants.BOOK_TOPIC_ID,groupId = "BOOK")
    public void getKafkaMessage(String message){
        log.info("收到消息--->{}",message);
    }
}
