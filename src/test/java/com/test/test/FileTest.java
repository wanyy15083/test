package com.test.test;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by songyigui on 2016/9/26.
 */
public class FileTest {
    @Test
    public void deleteTemp() {
        try {
            File file = new File("src/main/java/1.txt");
            FileUtils.write(file, "1234567", "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        /*File temp = null;
        try {
            temp = new File("1.txt");
            String result = FileUtils.readFileToString(temp, "UTF-8");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (temp.exists()) {
                temp.delete();
            }
        }*/
    }

//    @Test
//    public void testAsync() {
//        try {
//            File temp = new File("1.txt");
//            String result = FileUtils.readFileToString(temp, "UTF-8");
//            AsyncTask task = new AsyncTask();
//            task.deleteTemp(temp);
//            System.out.println(result);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

//class AsyncTask {
//    @Async("asyncExecutor")
//    public void deleteTemp(File file) {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        FileUtils.deleteQuietly(file);
//    }
//}