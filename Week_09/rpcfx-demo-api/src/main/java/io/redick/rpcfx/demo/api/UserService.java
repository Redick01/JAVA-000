package io.redick.rpcfx.demo.api;

import io.redick.rpcfx.demo.dto.User;

/**
 * @author liupenghui
 * @date 2020/12/15 8:18 下午
 */
public interface UserService {

    User findById(int id);
}
