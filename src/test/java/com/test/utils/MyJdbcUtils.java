package com.test.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;


public class MyJdbcUtils {

    // 驱动名称
    private static final String DRIVERCLASS;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    // 静态代码块
    static {
        // 读取properties属性文件
        Properties pro = new Properties();
        InputStream in = MyJdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
        // 加载
        try {
            pro.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取值
        DRIVERCLASS = pro.getProperty("jdbc.driver");
        URL = pro.getProperty("jdbc.url");
        USERNAME = pro.getProperty("jdbc.user");
        PASSWORD = pro.getProperty("jdbc.password");
    }

    /**
     * 获取链接
     *
     * @return
     * @throws Exception
     */
    public static Connection getConn() throws Exception {
        // 加载驱动
        Class.forName(DRIVERCLASS);
        // 返回链接
        /**
         * 如果访问本地的自己的MySQL的服务器，链接有简写的方法
         * jdbc:mysql:///day10
         */
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }


    /**
     * 释放资源（释放查询）
     *
     * @param rs
     * @param stmt
     * @param conn
     */
    public static void release(ResultSet rs, Statement stmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stmt = null;
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            conn = null;
        }
    }

    /**
     * 释放资源（增删改）
     *
     * @param stmt
     * @param conn
     */
    public static void release(Statement stmt, Connection conn) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stmt = null;
        }
        if (conn != null) {
            try {
                // 现在close是归还连接的方法，不是销毁连接
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            conn = null;
        }
    }

}
