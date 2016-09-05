package com.test.mediator;

/**
 * Created by songyigui on 2016/8/22.
 */
public class Iraq extends Country {
    public Iraq(UnitedNations mediator) {
        super(mediator);
    }

    public void declare(String message) {
        mediator.declare(message, this);

    }

    public void getMessage(String mesage) {
        System.out.println("伊拉克获得对方消息："+mesage);
    }
}
