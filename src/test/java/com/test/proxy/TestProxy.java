package com.test.proxy;

public class TestProxy {
	public static void main(String[] args) {
		
//		Proxy proxy = new Proxy();
//		proxy.Request();
//
//		System.out.println();

//		BookFacadeProxy proxy = new BookFacadeProxy();
//		BookFacade bookProxy = (BookFacade) proxy.bind(new BookFacadeImpl());
//		bookProxy.addBook();

		BookFacadeProxy1 proxy = new BookFacadeProxy1();
		BookFacadeImpl1 bookCglib = (BookFacadeImpl1) proxy.getInstance(new BookFacadeImpl1());
		bookCglib.addBook();
	}
}
