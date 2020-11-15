package com.spring.wprk.aop;

/**
 * @Author Redick
 * @Date 2020/11/14 4:05 下午
 */
public class HelloImpl implements IHello{

    @Override
    public void sayHello() {
        System.out.println("Hello");
    }
}
