package com.test.chain;

/**
 * Created by songyigui on 2016/8/16.
 */
public class Handler1 extends Handler {
    @Override
    public void handleRequest(int request) {
        if (request >= 0 && request < 10) {
            System.out.println("处理请求"+this.getClass().getName()+request);
        }else if (successor != null) {
            successor.handleRequest(request);
        }
    }
}
