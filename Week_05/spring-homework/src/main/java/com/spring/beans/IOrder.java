package com.spring.beans;

/**
 * @Author Redick
 * @Date 2020/11/14 4:57 下午
 */
public interface IOrder {

    /**
     * 订单支付
     * @param order
     * @return
     */
    ModelsReturn orderPay(Order order);

    /**
     * 根据订单号查询订单信息
     * @param orderId
     * @return
     */
    ModelsReturn getOrder(String orderId);
}
