package com.spring.wprk.aop;

/**
 * @Author Redick
 * @Date 2020/11/14 4:11 下午
 */
public class Main {

    public static void main(String[] args) {

        // 1.静态代理
        IHello hello = new HelloStaticProxy(new HelloImpl());
        hello.sayHello();

        // 2.JDK动态代理
        IHello hello1 = new HelloDynamicProxy<Object>(new HelloImpl()).getProxy();
        hello1.sayHello();

        // 3.CGLib动态代理，可以代理类，通过反射
        HelloImpl hello2 = HelloCGLibDynamicProxy.getInstance().getProxy(HelloImpl.class);
        hello2.sayHello();
    }
}
