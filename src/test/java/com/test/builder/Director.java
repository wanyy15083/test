package com.test.builder;

/**
 * Created by songyigui on 2016/8/15.
 */
public class Director {
    public void construct(Builder builder) {
        builder.buildPartA();
        builder.buildPartB();
    }
}
