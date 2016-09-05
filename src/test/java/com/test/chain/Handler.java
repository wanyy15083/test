package com.test.chain;

/**
 * Created by songyigui on 2016/8/16.
 */
abstract public class Handler {
    protected Handler successor;

    public void setSuccessor(Handler successor){
        this.successor = successor;
    }

    public abstract void handleRequest(int request);
}
