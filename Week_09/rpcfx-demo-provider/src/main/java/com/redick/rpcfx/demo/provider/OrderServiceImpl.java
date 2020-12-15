package com.redick.rpcfx.demo.provider;


import io.redick.rpcfx.demo.api.OrderService;
import io.redick.rpcfx.demo.dto.Order;

public class OrderServiceImpl implements OrderService {

    @Override
    public Order findOrderById(int id) {
        return new Order(id, "Cuijing" + System.currentTimeMillis(), 9.9f);
    }
}
