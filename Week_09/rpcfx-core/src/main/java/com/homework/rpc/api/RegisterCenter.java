package com.homework.rpc.api;

import java.util.List;

/**
 * @author liupenghui
 * @date 2020/12/15 7:05 下午
 */
public interface RegisterCenter {

    /**
     * register interface
     * @param serviceName interface name
     * @param port port
     */
    void register(String serviceName, Integer port);

    /**
     * get register impl name
     * @param serviceName interface name
     * @return interface impl name
     */
    String getImplName(String serviceName);

    /**
     * get impl list
     * @param nameSpace
     * @return
     * @throws Exception
     */
    List<String> getChildren(String nameSpace) throws Exception;
}
