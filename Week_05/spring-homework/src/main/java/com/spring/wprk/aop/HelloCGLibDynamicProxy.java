package com.spring.wprk.aop;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGlib动态代理，可以代理没有接口的类
 * @Author Redick
 * @Date 2020/11/14 4:21 下午
 */
public class HelloCGLibDynamicProxy implements MethodInterceptor {

    /**
     * 单例
     */
    private static HelloCGLibDynamicProxy proxy = new HelloCGLibDynamicProxy();

    private HelloCGLibDynamicProxy() {

    }

    public static HelloCGLibDynamicProxy getInstance() {
        return proxy;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Enhancer.create(clazz, this);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object obj = methodProxy.invokeSuper(o, objects);
        after();
        return obj;
    }

    private void before() {
        System.out.println("CGlib dynamic before");
    }

    private void after() {
        System.out.println("CGlib dynamic after");
    }
}
