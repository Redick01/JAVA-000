package com.order.impl;

import api.OrderService;
import dto.AddOrderRequestDTO;
import org.springframework.stereotype.Service;

/**
 * @author Redick
 * @date 2020/12/9 11:55 下午
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Override
    public boolean addOrder(AddOrderRequestDTO requestDTO) {
        return false;
    }
}
