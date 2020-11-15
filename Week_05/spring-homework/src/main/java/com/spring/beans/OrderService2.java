package com.spring.beans;

/**
 * @Author Redick
 * @Date 2020/11/14 5:51 下午
 */
public class OrderService2 {

    public Order getOrderByOrderId(String orderId) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderStatus("01");
        order.setOrderTotalAmount(2000);
        order.setDiscountAmount(100);
        order.setActualPayAmount(1900);
        order.setOrderRemark("1111");
        return order;
    }
}
