package com.test.template;

/**
 * Created by songyigui on 2016/8/15.
 */
abstract public class AbstractClass {
    public abstract void operation1();
    public abstract void operation2();

    public void templateMethod(){
        operation1();
        operation2();
        System.out.println("");
    }
}
