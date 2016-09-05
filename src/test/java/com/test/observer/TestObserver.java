package com.test.observer;

/**
 * Created by songyigui on 2016/8/15.
 */
public class TestObserver {
    public static void main(String[] args) {
        ConcreteSubject subject = new ConcreteSubject();
        subject.attach(new ConcreteObserver("X",subject));
        subject.attach(new ConcreteObserver("Y",subject));
        subject.attach(new ConcreteObserver("Z",subject));
        subject.setSubjectState("ABC");
        subject.notifyO();

    }
}
