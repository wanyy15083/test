package com.test.factory;

public class OperationDiv extends Operation {

	@Override
	public double getResult() {
		if (super.getNumberB() == 0) {
			throw new ArithmeticException("除数不能为0");
		}
		return super.getNumberA() / super.getNumberB();
	}
	
}
