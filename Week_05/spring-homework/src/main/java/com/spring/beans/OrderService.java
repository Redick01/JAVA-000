package com.spring.beans;

import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author Redick
 * @Date 2020/11/14 4:59 下午
 */
@Service
public class OrderService {

    public boolean pay(Order order) {
        if (Objects.isNull(order)) {
            return false;
        }
        order.setOrderRemark("支付成功");
        order.setOrderStatus("01");
        order.setActualPayAmount(900);
        order.setDiscountAmount(order.getOrderTotalAmount() - order.getActualPayAmount());
        return true;
    }
}
