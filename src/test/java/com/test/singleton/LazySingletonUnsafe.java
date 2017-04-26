package com.test.singleton;

/**
 * Created by songyigui on 2017/4/26.
 */
public class LazySingletonUnsafe {
    private static LazySingletonUnsafe instance;

    private LazySingletonUnsafe() {
    }

    public static LazySingletonUnsafe getInstance() {
        if (instance == null) {
            return new LazySingletonUnsafe();
        }
        return instance;
    }

}
