package com.test.mediator;

/**
 * Created by songyigui on 2016/8/22.
 */
abstract public class Country {
    protected UnitedNations mediator;
    public Country(UnitedNations mediator){
        this.mediator = mediator;
    }
}
