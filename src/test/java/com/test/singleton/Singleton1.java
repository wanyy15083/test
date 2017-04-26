package com.test.singleton;

/**
 * Created by songyigui on 2017/4/26.
 */
public class Singleton1 {
    private static Singleton1 instance = null;

    static {
        instance = new Singleton1();
    }

    private Singleton1() {
    }

    public static Singleton1 getInstance() {
        return instance;
    }
}
