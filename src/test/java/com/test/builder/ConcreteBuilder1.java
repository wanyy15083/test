package com.test.builder;

/**
 * Created by songyigui on 2016/8/15.
 */
public class ConcreteBuilder1 extends Builder {
    private Product product = new Product();
    @Override
    public void buildPartA() {
        product.add("部件A");
    }

    @Override
    public void buildPartB() {
        product.add("部件B");
    }

    @Override
    public Product getResult() {
        return product;
    }
}
