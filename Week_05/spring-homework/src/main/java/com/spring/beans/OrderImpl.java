package com.spring.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author Redick
 * @Date 2020/11/14 4:58 下午
 */
@Component("orderImpl")
@Slf4j
public class OrderImpl implements IOrder {

    @Autowired
    private OrderService orderService;

    private OrderService2 orderService2;

    public void setOrderService2(OrderService2 orderService2) {
        this.orderService2 = orderService2;
    }

    @Override
    public ModelsReturn orderPay(Order order) {
        try {
            boolean result = orderService.pay(order);
            if (result) {
                return new ModelsReturn("SUCCESS", "成功", null);
            }
        } catch (Exception e) {
            log.error("异常：{[]}", e);
        }
        return new ModelsReturn("FAILED", "失败", null);
    }

    @Override
    public ModelsReturn getOrder(String orderId) {
        try {
            return new ModelsReturn("SUCCESS", "成功", orderService2.getOrderByOrderId(orderId));
        } catch (Exception e) {
            log.error("异常：{[]}", e);
        }
        return new ModelsReturn("FAILED", "失败", null);
    }
}
