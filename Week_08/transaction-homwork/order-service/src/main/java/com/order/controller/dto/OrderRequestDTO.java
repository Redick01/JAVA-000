package com.order.controller.dto;

import lombok.Data;

/**
 * @author liupenghui
 * @date 2020/12/11 12:17 上午
 */
@Data
public class OrderRequestDTO {

    private String userId;

    private String productId;

    private Integer productCount;

    private Long totalAmount;
}
