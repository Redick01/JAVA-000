package com.homework.rpc.api;

import lombok.Data;

/**
 * @author Redick
 * @date 2020/12/14 11:06 下午
 */
@Data
public class RpcfxRequest {
    /**
     * service class
     */
    private String serviceClass;
    /**
     * method
     */
    private String method;

    /**
     * params
     */
    private Object[] params;
}
