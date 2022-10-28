package com.zsy.producer.config.kafka.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author 郑书宇
 * @create 2022/10/27 11:57
 * @desc
 */
@PropertySource("classpath:kafka-config.properties")
@ConfigurationProperties(prefix = "kafka")
@Data
@Component
public class KafkaProducerProperties {

    private String bootstrapServers;

    private Class keySerializer;

    private Class valueSerializer;
}
