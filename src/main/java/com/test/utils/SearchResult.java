package com.test.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询结果
 * @author Cheng.Biao
 * @Description:
 * @date 2015年9月10日下午3:40:06
 */
public class SearchResult implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
     * 记录总数
     */
    private long total = 0;

    /**
     * 记录行信息
     */
    private List<?> rows = new ArrayList<String>();


    /**
     * 构造函数
     */
    public SearchResult() {
    }

    /**
     * 构造函数
     * 
     * @param total
     *            记录总数
     * @param rows
     *            记录行信息
     */
    public SearchResult(long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    /**
     * 获取记录总数 * @return 记录总数
     */
    public long getTotal() {
        return total;
    }

    /**
     * 设置记录总数
     * 
     * @param total
     *            记录总数
     */
    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * 获取记录行信息
     * 
     * @return 记录行信息
     */
    public List<?> getRows() {
        return rows;
    }

    /**
     * 设置记录行信息
     * 
     * @param rows
     *            记录行信息
     */
    public void setRows(List<?> rows) {
        this.rows = rows;
    }

}
