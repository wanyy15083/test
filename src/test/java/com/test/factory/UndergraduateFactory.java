package com.test.factory;

/**
 * Created by songyigui on 2016/8/15.
 */
public class UndergraduateFactory implements IFactory {
    @Override
    public LeiFeng createLeiFeng() {
        return new Undergraduate();
    }
}
