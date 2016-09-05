package com.test.bridge;

/**
 * Created by songyigui on 2016/8/15.
 */
public class RedfinedAbstraction extends Abstration {
    @Override
    public void operation() {
        implementor.operation();
    }
}
