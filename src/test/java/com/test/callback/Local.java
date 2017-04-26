package com.test.callback;

/**
 * Created by songyigui on 2017/1/13.
 */
public class Local implements Callback,Runnable {
    private Remote remote;
    private String message;

    public Local(Remote remote, String message) {
        super();
        this.remote = remote;
        this.message = message;
    }

    public void sendMessage(){
        System.out.println(Thread.currentThread().getName());
        Thread thread = new Thread(this);
        thread.start();
        System.out.println("发送消息~~~本地！！！");
    }

    @Override
    public void call() {
        System.out.println("返回消息~~~！！！");
        System.out.println(Thread.currentThread().getName());
        Thread.interrupted();
    }

    @Override
    public void run() {
        remote.executeMessage(message,this);
    }

    public static void main(String[] args) {
        Local local = new Local(new Remote(),"Hello");
        local.sendMessage();
    }
}
