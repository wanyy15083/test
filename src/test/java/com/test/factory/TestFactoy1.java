package com.test.factory;

/**
 * Created by songyigui on 2016/8/15.
 */
public class TestFactoy1 {
    public static void main(String[] args) {
        IFactory factory = new UndergraduateFactory();
        LeiFeng student = factory.createLeiFeng();
        student.Sweep();
        student.Wash();
        student.BuyRice();


    }
}
