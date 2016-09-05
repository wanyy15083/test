package com.test.factory;

/**
 * Created by songyigui on 2016/8/15.
 */
public class VolunteerFactory implements IFactory {
    @Override
    public LeiFeng createLeiFeng() {
        return new Volunteer();
    }
}
