package com.test.memento;

/**
 * Created by songyigui on 2016/8/15.
 */
public class TestMemento {
    public static void main(String[] args) {
        Originator o = new Originator();
        o.setState("OK");
        o.show();

        Caretaker c = new Caretaker();
        c.setMemento(o.createMemento());

        o.setState("OFF");
        o.show();

        o.seteMemento(c.getMemento());
        o.show();
    }
}
