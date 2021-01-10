package com.homework.activemq;

import com.alibaba.fastjson.JSON;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * activeMQ消息生产者
 * @author liupenghui
 * @date 2021/1/10 11:14 上午
 */
public class ActiveMQProducer<T> {

    private static final String URL = "tcp://192.168.3.78:61616";

    /**
     * Queue Name
     */
    private static final String QUEUE_NAME = "queue-demo";

    /**
     * Topic Name
     */
    private static final String TOPIC_NAME = "topic-demo";

    /**
     * 生产消息，Topic 发布/订阅模式
     * @param t
     * @throws JMSException
     */
    public void sendTopic(T t) throws JMSException {
        Connection connection = null;
        Session session = null;
        try {
            //1.创建ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
            //2.创建连接
            connection = connectionFactory.createConnection();
            //3.启动连接
            connection.start();
            //4.创建会话
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            //5.创建topic
            Destination destination = session.createTopic(TOPIC_NAME);
            // 6.创建生产者
            MessageProducer producer = session.createProducer(destination);
            String message = JSON.toJSONString(t);
            TextMessage textMessage = session.createTextMessage(message);
            System.out.println("发送Topic消息：" + message);
            producer.send(textMessage);
            session.commit();
        } finally {
            if (null != session) {
                session.close();
            }
            if (null != connection) {
                connection.stop();
                connection.close();
            }
        }
    }


    /**
     * 生产消息，Queue模式
     * @param t
     * @throws JMSException
     */
    public void sendQueue(T t) throws JMSException {
        Connection connection = null;
        Session session = null;
        try {
            //1.创建ConnectionFactory
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
            //2.创建连接
            connection = connectionFactory.createConnection();
            //3.启动连接
            connection.start();
            //4.创建会话
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            //5.创建一个目标
            Destination destination = session.createQueue(QUEUE_NAME);
            // 6.创建生产者
            MessageProducer producer = session.createProducer(destination);
            String message = JSON.toJSONString(t);
            TextMessage textMessage = session.createTextMessage(message);
            System.out.println("发送Queue消息：" + message);
            producer.send(textMessage);
        } finally {
            if (null != session) {
                session.close();
            }
            if (null != connection) {
                connection.stop();
                connection.close();
            }
        }
    }
}
