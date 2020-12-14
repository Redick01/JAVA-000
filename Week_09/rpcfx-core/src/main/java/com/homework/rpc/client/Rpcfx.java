package com.homework.rpc.client;

import com.alibaba.fastjson.parser.ParserConfig;
import com.homework.rpc.api.RpcfxRequest;
import com.homework.rpc.api.RpcfxResponse;

import java.awt.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Redick
 * @date 2020/12/14 11:08 下午
 */
public class Rpcfx {

    static {
        ParserConfig.getGlobalInstance().addAccept("com.homework");
    }

    public static <T> T create(final Class<T> serviceClass, final String url) {

        // 0. 替换动态代理 -> AOP
        return (T) Proxy.newProxyInstance(Rpcfx.class.getClassLoader(), new Class[]{serviceClass}, new RpcfxInvocationHandler(serviceClass, url));

    }

    /**
     * JDK proxy
     */
    public static class RpcfxInvocationHandler implements InvocationHandler {

        private final Class<?> serviceClass;

        private final String url;

        public <T>   RpcfxInvocationHandler(Class<T> serviceClass, String url) {
            this.serviceClass = serviceClass;
            this.url = url;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return null;
        }

        /**
         * Netty HttpClient
         * @return rpc response
         */
        private RpcfxResponse doPost(RpcfxRequest req) {
            return new RpcfxResponse();
        }
    }
}
