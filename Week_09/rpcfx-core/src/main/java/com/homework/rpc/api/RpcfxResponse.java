package com.homework.rpc.api;

import lombok.Data;

/**
 * @author Redick
 * @date 2020/12/14 11:07 下午
 */
@Data
public class RpcfxResponse {

    private Object result;

    private boolean status;

    private Exception exception;
}
