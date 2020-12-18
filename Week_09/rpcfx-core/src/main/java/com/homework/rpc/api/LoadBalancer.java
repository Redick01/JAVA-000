package com.homework.rpc.api;

import java.util.List;

/**
 * @author liupenghui
 * @date 2020/12/18 11:29 下午
 */
public interface LoadBalancer {

    String select(List<String> urls);
}
