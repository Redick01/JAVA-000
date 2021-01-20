package com.redick.rpcfx.demo.provider;


import com.homework.rpc.annotation.Service;
import io.redick.kmq.rpcfx.demo.api.OrderService;
import io.redick.kmq.rpcfx.demo.dto.Order;

@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public Order findOrderById(int id) {
        return new Order(id, "Cuijing" + System.currentTimeMillis(), 9.9f);
    }
}
