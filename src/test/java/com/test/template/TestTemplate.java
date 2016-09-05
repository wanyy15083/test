package com.test.template;

/**
 * Created by songyigui on 2016/8/15.
 */
public class TestTemplate {
    public static void main(String[] args) {
        AbstractClass c;
        c = new ConcreteClassA();
        c.templateMethod();

        c = new ConcreteClassB();
        c.templateMethod();

    }
}
