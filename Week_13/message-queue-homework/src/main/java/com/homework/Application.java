package com.homework;
import com.homework.kafka.KafkaProducer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author liupenghui
 * @date 2021/1/10 7:28 下午
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.homework.kafka"})
public class Application {

    @Resource
    private KafkaProducer kafkaProducer;

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).run(args);
    }

    /**
     * 测试kafka集群消息生产和消费
     */
    @PostConstruct
    public void sendKafka() {
        for (int i = 0; i < 100; i++) {
            kafkaProducer.sendMessage("redick" + i);
        }
    }
}
