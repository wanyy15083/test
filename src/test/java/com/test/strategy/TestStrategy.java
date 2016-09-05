package com.test.strategy;

public class TestStrategy {

	public static void main(String[] args) {
		CashContext context = new CashContext("打八折");
		
		System.out.println(context.GetResult(100));
	}

}
