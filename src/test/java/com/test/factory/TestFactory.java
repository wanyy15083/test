package com.test.factory;

public class TestFactory {
	public static void main(String[] args) {
		Operation operation = OperationFactory.createOperate("+");
		operation.setNumberA(1);
		operation.setNumberB(2);
		System.out.println(operation.getResult());
	}
}
