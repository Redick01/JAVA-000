package com.homework.rpc.api;

/**
 * @author liupenghui
 * @date 2020/12/18 11:29 下午
 */
public interface Filter {

    boolean filter(RpcfxRequest request);
}
