package com.test.state;

/**
 * Created by songyigui on 2016/8/15.
 */
public class ConcreteStateB extends State {
    @Override
    public void handle(Context context) {
        context.setState(new ConcreteStateA());
    }
}
