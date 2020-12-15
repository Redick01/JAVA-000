package com.homework.rpc.api;

/**
 * @author liupenghui
 * @date 2020/12/15 7:05 下午
 */
public interface RegisterCenter {

    /**
     * register interface
     * @param serviceName interface name
     * @param implName interface impl name
     */
    void register(String serviceName, String implName);

    /**
     * get register impl name
     * @param serviceName interface name
     * @return interface impl name
     */
    String getImplName(String serviceName);
}
