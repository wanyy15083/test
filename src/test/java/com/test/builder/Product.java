package com.test.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songyigui on 2016/8/15.
 */
public class Product {
    List<String> parts = new ArrayList<String>();
    public void add(String part){
        parts.add(part);
    }
    public void show(){
        System.out.println("产品创建...");
        for (String part : parts) {
            System.out.println(part);
        }
    }
}
