package com.homework.rpc.api;

import java.util.List;

/**
 * @author liupenghui
 * @date 2020/12/18 11:28 下午
 */
public interface Router {

    List<String> route(List<String> invokers);
}
