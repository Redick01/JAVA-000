package io.redick.rpcfx.demo.dto;

import lombok.Data;

/**
 * @author liupenghui
 * @date 2020/12/15 8:14 下午
 */
@Data
public class Order {

    private int id;

    private String name;

    private float amount;

    public Order() {}

    public Order(int id, String name, float amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }
}
