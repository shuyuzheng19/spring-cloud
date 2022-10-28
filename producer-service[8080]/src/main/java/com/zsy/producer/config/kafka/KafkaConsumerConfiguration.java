package com.zsy.producer.config.kafka;

import com.zsy.producer.config.kafka.properties.KafkaConsumerProperties;
import com.zsy.producer.config.kafka.properties.KafkaProducerProperties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 郑书宇
 * @create 2022/10/27 11:56
 * @desc
 */
@Configuration
public class KafkaConsumerConfiguration {

    @Resource
    private KafkaConsumerProperties kafkaConsumerProperties;


    public Map<String,Object> kafkaConsumerConfigMap(){
        Map<String,Object> map=new HashMap<>();
        map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaConsumerProperties.getBootstrapServers());
        map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,kafkaConsumerProperties.getKeyDeserializer());
        map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,kafkaConsumerProperties.getValueDeserializer());
        return map;
    }

    @Bean
    public ConsumerFactory<String,Object> consumerFactory(){
        ConsumerFactory<String,Object> producerFactory=new DefaultKafkaConsumerFactory<>(kafkaConsumerConfigMap());
        return producerFactory;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,Object>> kafkaListenerContainerFactory(ConsumerFactory consumerFactory){
        ConcurrentKafkaListenerContainerFactory<String,Object> concurrentKafkaListenerContainerFactory=new ConcurrentKafkaListenerContainerFactory<>();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory);
        return concurrentKafkaListenerContainerFactory;
    }

}
