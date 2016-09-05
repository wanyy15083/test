package com.test.decorator;

public class TShirts extends Finery {

	@Override
	public void Show() {
		System.out.println("大T恤");
		super.Show();
	}

	
}
