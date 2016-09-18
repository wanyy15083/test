package com.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by songyigui on 2016/8/9.
 */
public class BookFacadeProxy implements InvocationHandler {
    private Object target;

    public Object bind(Object target) {
        this.target = target;
        return java.lang.reflect.Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        System.out.println("事物开始...");
        result = method.invoke(target,args);
        System.out.println("事物结束...");
        return result;
    }
}
