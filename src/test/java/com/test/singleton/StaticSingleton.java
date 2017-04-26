package com.test.singleton;

/**
 * 综合版
 * Created by songyigui on 2016/11/9.
 */
public class StaticSingleton {
    private StaticSingleton() {
        System.out.println("StaticSingleton is create!");
    }

    private static class SingletonHolder {
        private static StaticSingleton instance = new StaticSingleton();
    }

    public static StaticSingleton getInstance() {
        return SingletonHolder.instance;
    }
}
