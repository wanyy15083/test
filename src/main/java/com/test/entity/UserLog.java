package com.test.entity;

import java.io.Serializable;

/**
 * Created by songyigui on 2017/6/20.
 */
public class UserLog implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 编号
     *
     */
    private Integer logId;

    /**
     * 操作描述
     *
     */
    private String description;

    /**
     * 操作用户
     *
     */
    private String username;

    /**
     * 操作时间
     *
     */
    private Long startTime;

    /**
     * 消耗时间
     *
     */
    private Integer spendTime;

    /**
     * 根路径
     *
     */
    private String basePath;

    /**
     * URI
     *
     */
    private String uri;

    /**
     * URL
     *
     */
    private String url;

    /**
     * 请求类型
     *
     */
    private String method;

    /**
     * 用户标识
     *
     */
    private String userAgent;

    /**
     * IP地址
     *
     */
    private String ip;

    /**
     * 权限值
     */
    private String permissions;

    private String parameter;

    private String result;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Integer getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(Integer spendTime) {
        this.spendTime = spendTime;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
