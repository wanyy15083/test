package com.test.factory;

public class OperationMul extends Operation {

	@Override
	public double getResult() {
		return super.getNumberA() * super.getNumberB();
	}
	
}
