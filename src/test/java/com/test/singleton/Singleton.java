package com.test.singleton;

/**
 * 饿汉式
 * Created by songyigui on 2016/11/9.
 */
public class Singleton {
    private Singleton() {
        System.out.println("Singleton is create!");
    }

    private static Singleton instance = new Singleton();

    public static Singleton getInstance() {
        return instance;
    }

}
