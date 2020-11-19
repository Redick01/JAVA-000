package com.homework.singleton;

/**
 * 饿汉模式的单例
 * @Author Redick
 * @Date 2020/11/14 10:55 下午
 */
public class Singleton {

    private Singleton() {}

    private final static Singleton INSTANCE = new Singleton();

    public static Singleton getInstance() {
        return INSTANCE;
    }
}
