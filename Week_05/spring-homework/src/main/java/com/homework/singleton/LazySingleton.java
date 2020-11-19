package com.homework.singleton;

/**
 * 懒汉模式
 * @Author Redick
 * @Date 2020/11/14 10:57 下午
 */
public class LazySingleton {

    private LazySingleton() {}

    private static LazySingleton instance;

    public static LazySingleton getInstance() {
        if (null == instance) {
            synchronized (LazySingleton.class) {
                if (null == instance) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}
