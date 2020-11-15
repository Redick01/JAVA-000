package com.spring.wprk.aop;

/**
 * @Author Redick
 * @Date 2020/11/14 4:09 下午
 */
public class HelloStaticProxy implements IHello {

    private HelloImpl hello;

    public HelloStaticProxy(HelloImpl hello) {
        this.hello = hello;
    }

    @Override
    public void sayHello() {
        before();
        hello.sayHello();
        after();
    }

    private void before() {
        System.out.println("static before");
    }

    private void after() {
        System.out.println("static after");
    }
}
