package com.redick.rpcfx.demo.provider;

import io.redick.rpcfx.demo.api.UserService;
import io.redick.rpcfx.demo.dto.User;

public class UserServiceImpl implements UserService {

    @Override
    public User findById(int id) {
        return new User(id, "KK" + System.currentTimeMillis());
    }
}
