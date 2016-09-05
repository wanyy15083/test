package com.test.command;

/**
 * Created by songyigui on 2016/8/16.
 */
public class TestCommand {
    public static void main(String[] args) {
        Receiver r = new Receiver();
        Command c = new ConcreteCommand(r);
        Invoker i = new Invoker();
        i.setCommand(c);
        i.executeComand();
    }
}
