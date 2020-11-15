package com.spring.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author Redick
 * @Date 2020/11/14 4:33 下午
 */
@Slf4j
public class SpringMain {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        Order order = (Order) context.getBean("order");
        log.info("订单：[{}]", order.toString());
        IOrder iOrder = (OrderImpl) context.getBean("orderImpl");
        ModelsReturn modelsReturn = iOrder.orderPay(order);
        log.info("支付后订单：[{}]", order.toString());
        log.info("支付结果：[{}]", modelsReturn.toString());
        ModelsReturn modelsReturn1 = iOrder.getOrder(order.getOrderId());
        log.info("订单查询结果：[{}]", modelsReturn1.toString());
    }
}
