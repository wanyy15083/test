package com.test.flyweight;

/**
 * Created by songyigui on 2016/8/23.
 */
public class ConcreteFlyweight extends Flyweight {
    @Override
    public void operation(int extrinsicstate) {
        System.out.println("具体："+extrinsicstate);
    }
}
