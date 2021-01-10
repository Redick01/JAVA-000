package com.homework.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

/**
 * activeMQ消息消费者
 * @author liupenghui
 * @date 2021/1/10 11:25 上午
 */
public class ActiveMQConsumer {

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
     * 消费-Topic 订阅/发布模式
     * @throws JMSException
     */
    public void consumerTopic() throws JMSException {
        //1.创建ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);

        //2.创建连接
        Connection connection = connectionFactory.createConnection();

        //3.启动连接
        connection.start();

        //4.创建会话
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        //5.创建一个目标
        Destination destination = session.createTopic(TOPIC_NAME);
        //6.创建消费者
        MessageConsumer consumer = session.createConsumer(destination);

        //7.创建消息监听器
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage= (TextMessage) message;
                try {
                    System.out.println("消费Topic消息：" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 消费-Queue模式
     * @throws JMSException
     */
    public void consumerQueue() throws JMSException {
        //1.创建ConnectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);

        //2.创建连接
        Connection connection = connectionFactory.createConnection();

        //3.启动连接
        connection.start();
        //4.创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //5.创建一个目标
        Destination destination = session.createQueue(QUEUE_NAME);

        //6.创建消费者
        MessageConsumer consumer = session.createConsumer(destination);

        //7.创建消息监听器
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage= (TextMessage) message;
                try {
                    System.out.println("消费Queue消息：" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
