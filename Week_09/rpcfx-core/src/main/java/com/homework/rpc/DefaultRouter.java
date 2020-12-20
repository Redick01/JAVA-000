package com.homework.rpc;

import com.homework.rpc.api.Router;

import java.util.List;

/**
 * @author Redick
 * @date 2020/12/20 11:49 下午
 */
public class DefaultRouter implements Router {

    @Override
    public List<String> route(List<String> invokers) {
        return invokers;
    }
}
