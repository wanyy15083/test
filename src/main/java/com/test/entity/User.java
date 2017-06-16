package com.test.entity;

import java.io.Serializable;

/**
 * Created by songyigui on 2017/6/16.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 8941012353272388061L;

    private Integer userId;
    private String userName;
    private String password;
    private String email;
    private String createTime;
    private Integer status;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
