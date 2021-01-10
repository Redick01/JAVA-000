package com.homework.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消息生产者
 * @author liupenghui
 * @date 2021/1/10 7:56 下午
 */
@Component
@Slf4j
public class KafkaProducer {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        log.info("发送消息：{}", message);
        kafkaTemplate.send("test32", message);
    }

    public void sendMessage(String key, String message) {
        log.info("发送消息：{}", message);
        kafkaTemplate.send("test32", key, message);
    }
}
