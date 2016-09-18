package com.test.utils;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by songyigui on 2016/9/9.
 */
public class ListUtils {
    static Logger logger = LoggerFactory.getLogger(ListUtils.class);

    public static void main(String[] args) {
        List list1 = new ArrayList();
        list1.add("1111");
        list1.add("2222");
        list1.add("3333");

        List list2 = new ArrayList();
//        list2.add("6666");
//        list2.add("4444");
//        list2.add("5555");

        //并集
        //list1.addAll(list2);
        //交集
//        boolean b = list1.retainAll(list2);
//        System.out.println(b);
        //差集
        //list1.removeAll(list2);
        //无重复并集
//        list2.removeAll(list1);
//        list1.addAll(list2);
        ArrayList arrayList = Lists.newArrayList(CollectionUtils.union(list1, list2));
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());

        }
//        try {
//            int i = 1/0;
//        } catch (Exception e) {
////            e.printStackTrace();
//            logger.error("123:", list1, e.getMessage());
//        }

        //System.out.println("-----------------------------------\n");
        //printStr(list1);

    }

    public static void printStr(List list1) {
        for (int i = 0; i < list1.size(); i++) {
            System.out.println(list1.get(i));
        }
    }
}
