package com.homework.rpc.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.homework.rpc.ZkRegistry;
import com.homework.rpc.api.*;
import com.homework.rpc.exception.RpcfxException;
import com.homework.rpc.util.HttpClientUtil;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Redick
 * @date 2020/12/14 11:08 下午
 */
public class Rpcfx {

    static {
        ParserConfig.getGlobalInstance().addAccept("io.redick");
    }

    public static <T, filters> T createFromRegistry(final Class<T> serviceClass, final String zkUrl, Router router, LoadBalancer loadBalance, Filter filter) throws Exception {

        // 加filter之一

        // curator Provider list from zk
        RegisterCenter registry = new ZkRegistry(zkUrl, "Rpcfx");
        List<String> invokers = registry.getChildren("/");
        // 1. 简单：从zk拿到服务提供的列表
        // 2. 挑战：监听zk的临时节点，根据事件更新这个list（注意，需要做个全局map保持每个服务的提供者List）
        List<String> urls = router.route(invokers, serviceClass.getName());

        String url = loadBalance.select(urls); // router, loadbalance

        return (T) create(serviceClass, url, filter);

    }


    public static <T> T create(final Class<T> serviceClass, final String url, Filter... filters) throws Exception {

        // 0. 替换动态代理 -> AOP
        //return (T) Proxy.newProxyInstance(Rpcfx.class.getClassLoader(), new Class[]{serviceClass}, new RpcfxInvocationHandler(serviceClass, url));
        return (T) new ByteBuddy().subclass(Object.class)
                .implement(serviceClass)
                .intercept(InvocationHandlerAdapter.of(new RpcfxInvocationHandler(serviceClass, url)))
                .make()
                .load(serviceClass.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded()
                .newInstance();
    }

    /**
     * JDK proxy
     */
    public static class RpcfxInvocationHandler implements InvocationHandler {

        public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");

        private final Class<?> serviceClass;

        private final String url;

        public <T>   RpcfxInvocationHandler(Class<T> serviceClass, String url) {
            this.serviceClass = serviceClass;
            this.url = url;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            RpcfxRequest request = new RpcfxRequest();
            request.setServiceClass(this.serviceClass.getName());
            request.setMethod(method.getName());
            request.setParams(args);
            RpcfxResponse response = doPost(request, url);
            if (response.isStatus()) {
                return JSON.parse(response.getResult().toString());
            } else {
                throw new RpcfxException(response.getException());
            }
        }

        /**
         * HttpClient
         * @return rpc response
         */
        private RpcfxResponse doPost(RpcfxRequest req, String url) throws Exception {
            String reqJson = JSON.toJSONString(req);
            System.out.println("req json: "+reqJson);
           /* OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(JSONTYPE, reqJson))
                    .build();
            String respJson = client.newCall(request).execute().body().string();
            System.out.println("resp json: "+respJson);*/

            // 1.使用HttpClient实现，使用线程池处理请求
            HttpClientUtil httpClientUtil = new HttpClientUtil(url);
            String respJson = httpClientUtil.handle(reqJson);
            return JSON.parseObject(respJson, RpcfxResponse.class);
        }
    }
}
