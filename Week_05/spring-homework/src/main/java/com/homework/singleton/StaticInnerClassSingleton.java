package com.homework.singleton;

/**
 * @Author Redick
 * @Date 2020/11/14 11:15 下午
 */
public class StaticInnerClassSingleton {

    private StaticInnerClassSingleton() {}

    public static StaticInnerClassSingleton getInstance() {
        return Singleton.instance;
    }

    private static class Singleton {
        private static final StaticInnerClassSingleton instance = new StaticInnerClassSingleton();
    }
}
