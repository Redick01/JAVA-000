package com.spring.wprk.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理，只能代理接口
 * @Author Redick
 * @Date 2020/11/14 4:13 下午
 */
public class HelloDynamicProxy<T> implements InvocationHandler {

    private T object;

    public HelloDynamicProxy(T object) {
        this.object = object;
    }

    @SuppressWarnings("unchecked")
    public <T>T getProxy() {
        return (T) Proxy.newProxyInstance(
                object.getClass().getClassLoader(),
                object.getClass().getInterfaces(),
                this
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object obj = method.invoke(object, args);
        after();
        return obj;
    }

    private void before() {
        System.out.println("dynamic before");
    }

    private void after() {
        System.out.println("dynamic after");
    }
}
