package com.test.queue;

/**
 * Created by songyigui on 2017/6/13.
 */
public class Pair<K,V> {
    public K first;
    public V second;

    public Pair() {
    }

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }
}
