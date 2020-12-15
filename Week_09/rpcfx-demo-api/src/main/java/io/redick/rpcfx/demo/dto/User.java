package io.redick.rpcfx.demo.dto;

import lombok.Data;

/**
 * @author liupenghui
 * @date 2020/12/15 8:16 下午
 */
@Data
public class User {

    private int id;

    private String name;

    public User() {}

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
