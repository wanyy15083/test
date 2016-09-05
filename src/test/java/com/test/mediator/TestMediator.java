package com.test.mediator;

/**
 * Created by songyigui on 2016/8/22.
 */
public class TestMediator {
    public static void main(String[] args) {
        UnitedNationsSecurityCouncil UNSC = new UnitedNationsSecurityCouncil();
        USA c1 = new USA(UNSC);
        Iraq c2 = new Iraq(UNSC);

        UNSC.setColleague1(c1);
        UNSC.setColleague2(c2);

        c1.declare("不准研制核武器，否则打你");
        c2.declare("我们很健康很强壮");

    }
}
