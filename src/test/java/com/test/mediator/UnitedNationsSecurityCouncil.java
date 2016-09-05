package com.test.mediator;

/**
 * Created by songyigui on 2016/8/22.
 */
public class UnitedNationsSecurityCouncil extends UnitedNations {
    private USA colleague1;
    private Iraq colleague2;

    public void setColleague1(USA colleague1) {
        this.colleague1 = colleague1;
    }

    public void setColleague2(Iraq colleague2) {
        this.colleague2 = colleague2;
    }

    @Override
    public void declare(String mesage, Country colleague) {
        if (colleague == colleague1) {
            colleague2.getMessage(mesage);
        }else{
            colleague1.getMessage(mesage);
        }
    }
}
