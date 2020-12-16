package com.homework.rpc.exception;

import lombok.Getter;

/**
 * @author Redick
 * @date 2020/12/16 3:01 下午
 */
@Getter
public class RpcfxException extends RuntimeException {

    @Getter
    private String code;
    @Getter
    private String msg;

    public RpcfxException() {
    }

    public RpcfxException(String code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public RpcfxException(Throwable cause,String code,String msg){
        super(cause);
        this.code=code;
        this.msg=msg;
    }

    public RpcfxException(String message) {
        super(message);
        this.msg=message;
    }

    public RpcfxException(String message, Throwable cause) {
        super(message, cause);
        this.msg=cause.getMessage();
    }

    public RpcfxException(Throwable cause) {
        super(cause);
        this.msg=cause.getMessage();
    }
}
