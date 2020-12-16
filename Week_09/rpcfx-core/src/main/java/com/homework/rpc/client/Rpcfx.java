package com.homework.rpc.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.homework.rpc.api.RpcfxRequest;
import com.homework.rpc.api.RpcfxResponse;
import com.homework.rpc.exception.RpcfxException;
import com.homework.rpc.util.HttpClientUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Redick
 * @date 2020/12/14 11:08 下午
 */
public class Rpcfx {

    static {
        ParserConfig.getGlobalInstance().addAccept("io.redick");
    }

    public static <T> T create(final Class<T> serviceClass, final String url) {

        // 0. 替换动态代理 -> AOP
        return (T) Proxy.newProxyInstance(Rpcfx.class.getClassLoader(), new Class[]{serviceClass}, new RpcfxInvocationHandler(serviceClass, url));

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
