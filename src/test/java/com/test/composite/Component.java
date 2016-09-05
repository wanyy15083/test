package com.test.composite;

/**
 * Created by songyigui on 2016/8/15.
 */
abstract public class Component {
    protected String name;

    public Component(String name) {
        this.name = name;
    }

    public abstract void add(Component c);

    public abstract void remove(Component c);

    public abstract void display(int depth);

}
