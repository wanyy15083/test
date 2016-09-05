package com.test.command;

/**
 * Created by songyigui on 2016/8/16.
 */
public class ConcreteCommand extends Command {

    public ConcreteCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute() {
        receiver.action();
    }
}
