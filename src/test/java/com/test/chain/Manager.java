package com.test.chain;

/**
 * Created by songyigui on 2016/8/22.
 */
abstract public class Manager {
    protected String name;
    protected Manager superior;
    public Manager(String name){
        this.name = name;
    }
    public void setSuperior(Manager superior){
        this.superior = superior;
    }
    abstract public void requestApplications(Request request);
}
