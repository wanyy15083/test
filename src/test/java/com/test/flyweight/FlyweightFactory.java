package com.test.flyweight;

import java.util.Hashtable;

/**
 * Created by songyigui on 2016/8/23.
 */
public class FlyweightFactory {
    private Hashtable flyweights = new Hashtable();

    public FlyweightFactory() {
        flyweights.put("X",new ConcreteFlyweight());
        flyweights.put("Y",new ConcreteFlyweight());
        flyweights.put("Z",new ConcreteFlyweight());
    }

    public Flyweight getFlyweight(String key){
        return (Flyweight)flyweights.get(key);
    }
}
