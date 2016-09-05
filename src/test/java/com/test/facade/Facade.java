package com.test.facade;

/**
 * Created by songyigui on 2016/8/15.
 */
public class Facade {
    SubSystemOne one;
    SubSystemTwo two;
    SubSystemThree three;

    public Facade() {
        this.two = new SubSystemTwo();
        this.three = new SubSystemThree();
        this.one = new SubSystemOne();
    }

    public void methodA(){
        System.out.println("方法组A...");
        one.methodOne();
        two.methodTwo();
    }

    public void methodB(){
        System.out.println("方法组B...");
        one.methodOne();
        three.methodThree();
    }

}

