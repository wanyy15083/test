package com.test.distrubuted.lock;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by songyigui on 2017/6/14.
 */
public class MysqlPrimaryLock {
    private static DruidDataSource dataSource = new DruidDataSource();
//    static {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        String url = "jdbc:mysql://127.0.0.1:3306/test?user=root&password=1234";
//        try {
//            connection = DriverManager.getConnection(url);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    static {
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("1234");
        dataSource.setMaxActive(20);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * 加锁
     *
     * @param lockId
     */
    public void lock(String lockId) throws SQLException {
        acquire(lockId);
    }

    /**
     * 获取锁
     *
     * @param lockId
     * @return
     */
    public boolean acquire(String lockId) {
        String sql = "INSERT INTO test_lock (id, count, thread_name, add_time) VALUES (?, ?, ?, ?)";
        while (true) {
            Connection connection = null;
            PreparedStatement statement = null;
            try {
                connection = getConnection();
                statement = connection.prepareStatement(sql);
                statement.setString(1, lockId);
                statement.setInt(2, 1);
                statement.setString(3, Thread.currentThread().getName());
                statement.setLong(4, System.currentTimeMillis());
                boolean success = statement.execute();
                if (success)
                    return true;
            } catch (SQLException e) {
                e.printStackTrace();

            } finally {
                release(statement, connection);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            continue;
        }
    }

    /**
     * 超时获取锁
     *
     * @param lockId
     * @param timeout
     * @return
     */
    public boolean acquire(String lockId, long timeout) throws Exception {
        String sql = "INSERT INTO test_lock (id, count, thread_name, add_time) VALUES (?, ?, ?, ?)";
        long futureTime = System.currentTimeMillis() + timeout;
        long remain = timeout;
        long timerange = 500;
        while (true) {
            CountDownLatch latch = new CountDownLatch(1);
            Connection connection = null;
            PreparedStatement statement = null;
            try {
                connection = getConnection();
                statement = connection.prepareStatement(sql);
                statement.setString(1, lockId);
                statement.setInt(2, 1);
                statement.setString(3, Thread.currentThread().getName());
                statement.setLong(4, System.currentTimeMillis());
                boolean success = statement.execute();
                if (success)
                    return true;
            } catch (SQLException e) {
                e.printStackTrace();

            } finally {
                release(statement, connection);
            }
            latch.await(timerange, TimeUnit.MILLISECONDS);
            remain = futureTime - System.currentTimeMillis();
            if (remain <= 0) {
                break;
            }
            if (remain < timerange) {
                timerange = remain;
            }
            continue;

        }
        return false;
    }

    /**
     * 释放锁
     *
     * @param lockId
     * @return
     */
    public boolean unlock(String lockId) {
        String sql = "DELETE FROM test_lock where id=?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, lockId);
            boolean success = statement.execute();
            if (success)
                return true;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            release(statement, connection);
        }
        return false;
    }

    /**
     * 超时获取锁
     *
     * @param lockId
     * @param timeout
     * @return
     * @throws InterruptedException
     */
    public boolean acquireByUpdate(String lockId, long timeout, Connection connection) throws Exception {

        String sql = "SELECT id from test_lock where id = ? for UPDATE ";
        long futureTime = System.currentTimeMillis() + timeout;
        long ranmain = timeout;
        long timerange = 500;
        connection.setAutoCommit(false);
        while (true) {
            CountDownLatch latch = new CountDownLatch(1);
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(sql);
                statement.setString(1, lockId);
                boolean sucess = statement.execute();//如果成功，那么就是获取到了锁
                if (sucess)
                    return true;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                release(statement, null);
            }
            latch.await(timerange, TimeUnit.MILLISECONDS);
            ranmain = futureTime - System.currentTimeMillis();
            if (ranmain <= 0)
                break;
            if (ranmain < timerange) {
                timerange = ranmain;
            }
            continue;
        }
        return false;

    }


    /**
     * 释放锁
     *
     * @param lockId
     * @return
     * @throws SQLException
     */
    public void unlockforUpdtate(String lockId, Connection connection) throws SQLException {
        connection.commit();
        connection.close();
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
