package com.zsy.producer.config.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.zsy.producer.config.kafka.properties.KafkaProducerProperties;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 郑书宇
 * @create 2022/10/27 11:56
 * @desc
 */
@Configuration
public class KafkaConfiguration {

    @Resource
    private KafkaProducerProperties kafkaProducerProperties;



    public Map<String,Object> kafkaConfigMap(){
        System.out.println("生产则--->"+kafkaProducerProperties);
        Map<String,Object> map=new HashMap<>();
        map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaProducerProperties.getBootstrapServers());
        map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,kafkaProducerProperties.getKeySerializer());
        map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,kafkaProducerProperties.getValueSerializer());
        return map;
    }

    @Bean
    public ProducerFactory<String,Object> producerFactory(){
        ProducerFactory<String,Object> producerFactory=new DefaultKafkaProducerFactory<>(kafkaConfigMap());
        return producerFactory;
    }

    @Bean
    public KafkaTemplate<String,Object> kafkaTemplate(ProducerFactory<String,Object> producerFactory){
        KafkaTemplate<String,Object> kafkaTemplate=new KafkaTemplate<>(producerFactory);
        return kafkaTemplate;
    }

    @Bean
    public RecordMessageConverter recordMessageConverter(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        RecordMessageConverter converter=new StringJsonMessageConverter(mapper);
        return converter;
    }
}
