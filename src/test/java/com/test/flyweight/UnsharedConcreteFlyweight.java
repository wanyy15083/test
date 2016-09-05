package com.test.flyweight;

/**
 * Created by songyigui on 2016/8/23.
 */
public class UnsharedConcreteFlyweight extends Flyweight {
    @Override
    public void operation(int extrinsicstate) {
        System.out.println("不共享："+extrinsicstate);
    }
}
