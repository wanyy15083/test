package com.test.factory;

public class OperationAdd extends Operation {

	@Override
	public double getResult() {
		return super.getNumberA() + super.getNumberB();
	}

}
