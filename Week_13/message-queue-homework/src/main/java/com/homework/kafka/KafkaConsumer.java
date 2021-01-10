package com.homework.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 消息消费者
 * @author liupenghui
 * @date 2021/1/10 7:56 下午
 */
@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "test32")
    public void listener(ConsumerRecord<String, String> record) {
        String value = record.value();
        log.info("===>consume message : {}", value);
    }
}
