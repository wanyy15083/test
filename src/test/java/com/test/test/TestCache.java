package com.test.test;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by songyigui on 2016/11/21.
 */
public class TestCache {
    private static Cache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .expireAfterWrite(12, TimeUnit.HOURS)
            .build();

    public static String getValue(final String key) throws ExecutionException {
        return cache.get(key, new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("from cache" + key);
                return getValue1(key);
            }
        });
    }

    private static String getValue1(String key) {
        System.out.println("from query" + key);
        return key.toUpperCase();
    }

    public static void main(String[] args) throws ExecutionException {
//        System.out.println(getValue("abcd"));
        System.out.println(getValue("abcd"));
        System.out.println(cache.getIfPresent("abcd"));
        System.out.println(cache.getIfPresent("abcd"));
    }

}
