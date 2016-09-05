package com.test.decorator;

public class TestDecorator {

	public static void main(String[] args) {
		Person person = new Person("张三");
		System.out.println("\n第一种打扮：");
		TShirts ts = new TShirts();
		Jeans jn = new Jeans();
		
		ts.Decorate(person);
		jn.Decorate(ts);
		jn.Show();
		
	}
}
