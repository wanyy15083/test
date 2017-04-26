package com.test.singleton;

/**
 * 懒汉式
 * Created by songyigui on 2016/11/9.
 */
public class LazySingleton {
    private LazySingleton() {
        System.out.println("LazySingleton is create!");
    }

    private static LazySingleton instance = null;

    public static synchronized LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
