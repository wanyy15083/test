package com.test.adapter;

/**
 * Created by songyigui on 2016/8/15.
 */
public class Adapter extends Target {

    private Adaptee adaptee = new Adaptee();

    @Override
    public void request() {
        adaptee.specificRequest();
    }
}
