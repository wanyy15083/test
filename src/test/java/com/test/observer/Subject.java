package com.test.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songyigui on 2016/8/15.
 */
abstract public class Subject {
    private List<Observer> observers = new ArrayList<Observer>();

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void notifyO() {
        for (Observer observer : observers) {
            observer.update();
        }


    }


}
