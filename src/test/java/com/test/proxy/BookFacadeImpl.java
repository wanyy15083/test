package com.test.proxy;

/**
 * Created by songyigui on 2016/8/9.
 */
public class BookFacadeImpl implements BookFacade {
    @Override
    public void addBook() {
        System.out.println("增加图书成功...");
    }
}
