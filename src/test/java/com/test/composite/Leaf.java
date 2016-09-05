package com.test.composite;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by songyigui on 2016/8/15.
 */
public class Leaf extends Component {

    public Leaf(String name) {
        super(name);
    }

    @Override
    public void add(Component c) {
        System.out.println("can not add a leaf");
    }

    @Override
    public void remove(Component c) {
        System.out.println("can not remove a leaf");
    }

    @Override
    public void display(int depth) {
        System.out.println(StringUtils.repeat("-",depth)+name);
    }
}
