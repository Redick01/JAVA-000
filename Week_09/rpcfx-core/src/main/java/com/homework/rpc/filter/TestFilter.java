package com.homework.rpc.filter;

import com.homework.rpc.api.Filter;
import com.homework.rpc.api.RpcfxRequest;

/**
 * @author liupenghui
 * @date 2020/12/21 8:42 下午
 */
public class TestFilter implements Filter {

    @Override
    public boolean filter(RpcfxRequest request) {
        return false;
    }
}
