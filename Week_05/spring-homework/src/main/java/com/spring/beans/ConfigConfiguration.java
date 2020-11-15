package com.spring.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Redick
 * @Date 2020/11/14 5:24 下午
 */
@Configuration
public class ConfigConfiguration {

    @Bean(name = "order1")
    public Order getOrder() {
        Order order = new Order();
        order.setOrderId("20201114172727233533");
        order.setOrderTotalAmount(2000);
        order.setOrderStatus("00");
        return order;
    }
}
