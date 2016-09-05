package com.test.composite;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by songyigui on 2016/8/15.
 */
public class Composite extends Component {
    private List<Component> children = new ArrayList<Component>();
    public Composite(String name) {
        super(name);
    }

    @Override
    public void add(Component c) {
        children.add(c);
    }

    @Override
    public void remove(Component c) {
        children.remove(c);
    }

    @Override
    public void display(int depth) {
        System.out.println(StringUtils.repeat("-", depth)+name);
        for (Component c : children) {
            c.display(depth+2);
        }

        Iterator iterator = children.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
