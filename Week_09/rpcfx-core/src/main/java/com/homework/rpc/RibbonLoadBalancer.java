package com.homework.rpc;

import com.homework.rpc.api.LoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.LoadBalancerBuilder;
import com.netflix.loadbalancer.Server;


import java.util.ArrayList;
import java.util.List;

/**
 * @author Redick
 * @date 2020/12/20 11:50 下午
 */
public class RibbonLoadBalancer implements LoadBalancer {


    @Override
    public String select(List<String> strings) {
        List<Server> serverList = new ArrayList<>();
        for (String serverClass : strings) {
            Server server = new Server(serverClass);
            serverList.add(server);
        }
        ILoadBalancer iLoadBalancer = LoadBalancerBuilder.newBuilder().buildFixedServerListLoadBalancer(serverList);
        Server server = iLoadBalancer.chooseServer(null);
        return server.getId();
    }
}
