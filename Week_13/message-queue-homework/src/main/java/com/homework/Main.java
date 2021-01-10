package com.homework;

import com.homework.activemq.ActiveMQConsumer;
import com.homework.activemq.ActiveMQProducer;

/**
 * @author liupenghui
 * @date 2021/1/10 10:53 上午
 */
public class Main {

    public static void main(String[] args) throws Exception {
        // activeMQ 测试
        ActiveMQProducer<String> producer = new ActiveMQProducer<>();
        ActiveMQConsumer consumer = new ActiveMQConsumer();

        // Queue 模式
        producer.sendQueue("redick");
        consumer.consumerQueue();

        // Topic模式要先启动消费者再启动生产者，顺序不可颠倒
        consumer.consumerTopic();
        producer.sendTopic("redick01");

    }
}
