package com.test.strategy;

public class CashContext {
	/*private CashSuper cs;
	
	public CashContext(CashSuper csuper){
		this.cs = csuper;
	}
	
	public double GetResult(double money){
		return cs.acceptCash(money);
	}*/
	
	//与简单工厂结合
	CashSuper cs = null;
	public CashContext(String type){
		switch (type) {
		case "正常收费":
			CashNormal cs0 = new CashNormal();
			cs = cs0;
			break;
		case "满300返100":
			CashReturn cr1 = new CashReturn("300", "100");
			cs = cr1;
			break;
		case "打八折":
			CashRebate cr2 = new CashRebate("0.8");
			cs = cr2;
			break;

		default:
			break;
		}
	}
	
	public double GetResult(double money){
		return cs.acceptCash(money);
	}
}
