package com.redick.rpcfx.demo.provider;

import com.homework.rpc.annotation.Service;
import io.redick.kmq.rpcfx.demo.api.UserService;
import io.redick.kmq.rpcfx.demo.dto.User;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User findById(int id) {
        return new User(id, "KK" + System.currentTimeMillis());
    }
}
