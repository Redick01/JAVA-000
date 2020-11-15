package com.spring.beans;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Redick
 * @Date 2020/11/14 4:37 下午
 */
@Data
public class Order implements Serializable {

    /**
     * 订单号
     */
    private String orderId;
    /**
     * 订单状态
     */
    private String orderStatus;
    /**
     * 订单总金额
     */
    private Integer orderTotalAmount;
    /**
     * 实际支付金额
     */
    private Integer actualPayAmount;
    /**
     * 优惠金额
     */
    private Integer discountAmount;
    /**
     * 订单备注
     */
    private String orderRemark;
}
