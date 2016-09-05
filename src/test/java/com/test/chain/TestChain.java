package com.test.chain;

/**
 * Created by songyigui on 2016/8/22.
 */
public class TestChain {
    public static void main(String[] args) {
        CommonManager tom = new CommonManager("Tom");
        Majordomo peter = new Majordomo("Peter");
        GeneralManager bob = new GeneralManager("Bob");
        tom.setSuperior(peter);
        peter.setSuperior(bob);

        Request request = new Request();
        request.setRequestType("请假");
        request.setRequestContent("生病");
        request.setNumber(1);
        tom.requestApplications(request);

        Request request1 = new Request();
        request1.setRequestType("加薪");
        request1.setRequestContent("到年限了");
        request1.setNumber(1000);
        tom.requestApplications(request1);
    }
}
