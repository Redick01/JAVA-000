package com.homework.rpc;

import com.homework.rpc.api.LoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.LoadBalancerBuilder;
import com.netflix.loadbalancer.Server;


import java.util.List;

/**
 * @author Redick
 * @date 2020/12/20 11:50 下午
 */
public class RibbonLoadBalancer implements LoadBalancer {

    private final ILoadBalancer iLoadBalancer;


    public RibbonLoadBalancer(List<Server> serverList) {
        iLoadBalancer = LoadBalancerBuilder.newBuilder().buildFixedServerListLoadBalancer(serverList);
    }


    @Override
    public String select(List<String> serverList) {
        return iLoadBalancer.chooseServer(serverList).getHost();
    }
}
