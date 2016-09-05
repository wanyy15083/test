package com.test.factory;

public class OperationSub extends Operation {

	@Override
	public double getResult() {
		return super.getNumberA() - super.getNumberB();
	}
	
}
