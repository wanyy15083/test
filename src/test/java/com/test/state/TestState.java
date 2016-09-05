package com.test.state;

/**
 * Created by songyigui on 2016/8/15.
 */
public class TestState {
    public static void main(String[] args) {
        Context c = new Context(new ConcreteStateA());
        c.request();
        c.request();
        c.request();
        c.request();

    }
}
