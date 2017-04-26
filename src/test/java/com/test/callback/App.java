package com.test.callback;

/**
 * Created by songyigui on 2017/1/13.
 */
public class App {

    public static void main(String[] args) {
        Task task = new SimpleTask();
        Callback callback = new Callback() {
            @Override
            public void call() {
                System.out.println("扫完了");
            }
        };
        task.executeWith(callback);
    }
}
