package com.test.command;

/**
 * Created by songyigui on 2016/8/16.
 */
abstract public class Command {
    protected Receiver receiver;

    public Command(Receiver receiver){
        this.receiver = receiver;
    }
    abstract public void execute();
}
