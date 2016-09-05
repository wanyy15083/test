package com.test.command;

/**
 * Created by songyigui on 2016/8/16.
 */
public class Invoker {
    private Command command;

    public void setCommand(Command command){
        this.command = command;
    }

    public void executeComand(){
        command.execute();
    }
}
