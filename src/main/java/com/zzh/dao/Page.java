package com.zzh.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhao on 2017/6/27.
 * 分页对象，包含当前页数据及分页信息如总记录数
 */
public class Page implements Serializable{

    private static int DEFAULT_PAGE_SIZE = 20;

    //每页的记录数
    private int pageSize = DEFAULT_PAGE_SIZE;

    //当前页第一条数据在List中的位置，从0开始
    private long start;

    //当前页中存放的记录，类型一般为List
    private List data;

    //总记录数
    private long totalCount;

    //构造空页
    public Page() {
        this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList());
    }

    /**
     *
     * @param start 本页数据在数据库中的起始位置
     * @param totalSize 数据库中总记录条数
     * @param pageSize 本页容量
     * @param data 本页包含的数据
     */
    public Page(long start, long totalSize, int pageSize, List data) {
        this.pageSize = pageSize;
        this.start = start;
        this.totalCount = totalSize;
        this.data = data;
    }

    //取总记录数
    public long getTotalCount() {
        return this.totalCount;
    }

    public long getTotalPageCount() {
        if (totalCount % pageSize == 0) {
            return totalCount / pageSize;
        } else {
            return totalCount / pageSize + 1;
        }
    }

    //取每页数据容量
    public int getPageSize() {
        return pageSize;
    }

    //取当前页中的记录（注意是当前页）
    public List getResult() {
        return data;
    }

    /**
     * 取当前页码
     * start为每页第一条记录的索引值，第一页第一条记录为0
     * 页码要从1开始所以需要+1
     * @return
     */
    public long getCurrentPageNo() {
        return start / pageSize + 1;
    }

    //该页是否有下一页
    public boolean isHasNextPage() {
        return this.getCurrentPageNo() < this.getTotalPageCount();
    }

    //该页是否有上一页
    public boolean isHasPreviousPage() {
        return this.getCurrentPageNo() > 1;
    }

    /**
     * 获取任一页第一条数据在数据集的位置，每页条数使用默认值.
     * @param pageNo
     * @return
     */
    protected static int getStartOfPage(int pageNo) {
        return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
    }

    /**
     * 获取任一页第一条数据在数据集的位置.
     *
     * @param pageNo   从1开始的页号
     * @param pageSize 每页记录条数
     * @return 该页第一条数据
     */
    public static int getStartOfPage(int pageNo, int pageSize) {
        return (pageNo - 1) * pageSize;
    }
}
