package com.homework.rpc.router;

import com.homework.rpc.api.Router;

import java.util.Arrays;
import java.util.List;

/**
 * @author Redick
 * @date 2020/12/20 11:49 下午
 */
public class DefaultRouter implements Router {

    @Override
    public List<String> route(List<String> invokers) {
        for (String invoker : invokers) {
            return Arrays.asList(invoker);
        }
        return invokers;
    }
}
