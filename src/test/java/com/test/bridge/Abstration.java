package com.test.bridge;

/**
 * Created by songyigui on 2016/8/15.
 */
public class Abstration {
    protected Implementor implementor;

    public Implementor getImplementor() {
        return implementor;
    }

    public void setImplementor(Implementor implementor) {
        this.implementor = implementor;
    }

    public void operation(){
        implementor.operation();
    }
}
