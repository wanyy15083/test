package com.test.test;

import org.junit.Test;

public class TestJson {

    @Test
    public void testGson() {
        // 转json
        /*
         * Book book = new Book(); book.setId("123"); book.setName("十万个为什么"); Gson gson = new
         * Gson(); String json = gson.toJson(book);
         * System.out.println(json);//{"id":"123","name":"十万个为什么"}
         */
        // 转bean
        /*
         * Gson gson = new Gson(); String json = "{\"id\":\"222\",\"name\":\"Json技术\"}"; Book book =
         * gson.fromJson(json, Book.class); System.out.println(book);
         */

        // 转list、set
        /*
         * Gson gson = new Gson(); String json =
         * "[{\"id\":\"222\",\"name\":\"Json技术\"},{\"id\":\"333\",\"name\":\"Java技术\"}]"; List list
         * = gson.fromJson(json, new TypeToken<List>() {}.getType()); Set set = gson.fromJson(json,
         * new TypeToken<Set>() {}.getType()); System.out.println(list); System.out.println(set);
         */

        // 格式化json
        /*
         * String json = "[{\"id\":\"1\",\"name\":\"Json技术\"},{\"id\":\"2\",\"name\":\"java技术\"}]";
         * Gson gson = new GsonBuilder().setPrettyPrinting().create(); JsonParser parser = new
         * JsonParser(); JsonElement element = parser.parse(json); String json2 =
         * gson.toJson(element); System.out.println(json2);
         */

        // 判断是否是json
        /*
         * String json = "[{\"id\":\"1\",\"name\":\"Json技术\"},{\"id\":\"2\",\"name\":\"java技术\"}]";
         * boolean jsonFlag = true; try { new JsonParser().parse(json).getAsJsonObject(); } catch
         * (Exception e) { jsonFlag = false; e.printStackTrace(); } System.out.println(jsonFlag);
         */

        // 从json中获取属性
        /*
         * String json = "{\"id\":\"1\",\"name\":\"Json技术\"}"; String propertyName = "id"; String
         * propertyValue = ""; try { JsonParser parser = new JsonParser(); JsonElement element =
         * parser.parse(json); JsonObject object = element.getAsJsonObject(); propertyValue =
         * object.get(propertyName).toString(); } catch (Exception e) { propertyValue = null;
         * e.printStackTrace(); } System.out.println(propertyName + ":" +propertyValue);
         */

        // 向json中添加属性
        /*
         * String json = "{\"id\":\"1\",\"name\":\"Json技术\"}"; String propertyName = "desc"; Object
         * propertyValue = "json各种技术的调研"; JsonParser parser = new JsonParser(); JsonElement element
         * = parser.parse(json); JsonObject object = element.getAsJsonObject();
         * object.addProperty(propertyName, new Gson().toJson(propertyValue)); json =
         * object.toString(); System.out.println(json);
         */

        // 修改json中的属性
        // 判断json中是否有属性
        /*
         * String json = "{\"id\":\"1\",\"name\":\"Json技术\"}"; String propertyName = "name"; boolean
         * isHas = false ; JsonParser jsonParser = new JsonParser(); JsonElement element =
         * jsonParser.parse(json); JsonObject jsonObj = element.getAsJsonObject(); isHas =
         * jsonObj.has(propertyName); System.out.println(isHas);
         */

        // 日期处理
        /*GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Gson gson = builder.create();
        String json = gson.toJson(new Date());
        System.out.println(json);*/
        
        
        
    }
}
