package com.test.bridge;

/**
 * Created by songyigui on 2016/8/15.
 */
public class TestBridge {
    public static void main(String[] args) {
        Abstration ab = new RedfinedAbstraction();
        ab.setImplementor(new ConcreteImplementorA());
        ab.operation();
        ab.setImplementor(new ConcreteImplementorB());
        ab.operation();
    }
}
