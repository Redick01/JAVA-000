package io.redick.rpcfx.demo.api;

import io.redick.rpcfx.demo.dto.Order;

/**
 * @author liupenghui
 * @date 2020/12/15 8:17 下午
 */
public interface OrderService {

    Order findOrderById(int id);
}
