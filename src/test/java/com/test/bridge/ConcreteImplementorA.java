package com.test.bridge;

/**
 * Created by songyigui on 2016/8/15.
 */
public class ConcreteImplementorA extends Implementor {
    @Override
    public void operation() {
        System.out.println("具体实现A的方法执行");
    }
}
