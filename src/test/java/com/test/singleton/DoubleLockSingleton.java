package com.test.singleton;

/**
 * Created by songyigui on 2017/4/26.
 */
public class DoubleLockSingleton {

    private volatile static DoubleLockSingleton instance;

    private DoubleLockSingleton() {
    }

    public static DoubleLockSingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleLockSingleton.class) {
                if (instance == null) {
                    instance = new DoubleLockSingleton();
                }
            }
        }
        return instance;
    }
}
