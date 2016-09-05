package com.test.test;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.Date;

public class TestFastJson {

    @Test
    public void testFastJson() {
        // 转json
        /*
         * Book book = new Book(); book.setId("123"); book.setName("十万个为什么"); //是否格式化 String json =
         * JSON.toJSONString(book, true); System.out.println(json);
         */
        // 复杂对象转json有问题
        /*
         * Student stu = new Student(); Set books = new HashSet(); Book book = new Book();
         * stu.setBooks(books); List list = new ArrayList(); for (int i = 0; i < 5; i++) {
         * list.add(stu); } String json = JSON.toJSONString(list, true); System.out.println(json);
         */
        // 转bean
        /*
         * String json = "{\"id\":\"2\",\"name\":\"Json技术\"}"; Book book = JSON.parseObject(json,
         * Book.class); System.out.println(book);
         */
        // 转list、set
        /*
         * String json = "[{\"id\":\"1\",\"name\":\"Json技术\"},{\"id\":\"2\",\"name\":\"java技术\"}]";
         * List list = JSON.parseObject(json, new TypeReference<ArrayList>(){}); Set set =
         * JSON.parseObject(json, new TypeReference<Set>(){}); System.out.println(list);
         * System.out.println(set);
         */
        // 从json串中获取属性
        /*
         * String propertyName = "id"; String propertyValue = ""; String json =
         * "{\"id\":\"1\",\"name\":\"Json技术\"}"; JSONObject obj = JSON.parseObject(json);
         * propertyValue = (String) obj.get(propertyName); System.out.println(propertyValue);
         */
        // 除去json中的某个属性
        /*
         * String propertyName = "id"; String propertyValue = ""; String json =
         * "{\"id\":\"1\",\"name\":\"Json技术\"}"; JSONObject obj = JSON.parseObject(json); Set set =
         * obj.keySet(); set.remove(propertyName); json = obj.toString(); System.out.println(json);
         */

        // 向json中添加属性
        /*
         * String propertyName = "desc"; Object propertyValue = "json详解"; String json =
         * "{\"id\":\"1\",\"name\":\"Json技术\"}"; JSONObject obj = JSON.parseObject(json);
         * obj.put(propertyName, JSON.toJSONString(propertyValue)); json = obj.toString();
         * System.out.println(json);
         */
        // 修改json中的属性
        /*
         * String propertyName = "name"; Object propertyValue = "json详解"; String json =
         * "{\"id\":\"1\",\"name\":\"Json技术\"}"; JSONObject obj = JSON.parseObject(json);
         * obj.put(propertyName, JSON.toJSON(propertyValue)); json = obj.toString();
         * System.out.println(json);
         */
        // 判断json中是否有属性
        /*
         * String propertyName = "name"; boolean isContain = false; String json =
         * "{\"id\":\"1\",\"name\":\"Json技术\"}"; JSONObject obj = JSON.parseObject(json); Set set =
         * obj.keySet(); isContain = set.contains(propertyName); System.out.println(isContain);
         */
        
        //日期处理
        String json = JSON.toJSONStringWithDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
        System.out.println(json);
    }
}
