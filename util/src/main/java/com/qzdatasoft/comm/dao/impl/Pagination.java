/**
 * 说明：分页对象，公共类
 * 编写者：谢平
 * 日期：Jul 27, 2007
 * 湖南强智科技版权所有。
 */
package com.qzdatasoft.comm.dao.impl;

import java.util.Collection;

/**
 * <p>
 * Title:分页对象
 * <p>
 * Description:数据分页DTO，公共类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: 湖南强智科技发展有限公司
 * </p>
 * 
 */
public class Pagination
{
    // 记录集
    private Collection rowSet;

    // 记录集大小
    private long rowSize = 0;

    // 第几页
    private int pageIndex = 1;

    // 页大小
    private int pageSize = 30;

    /**
     * 是否有首页
     */
    private boolean hasFirst = false;

    /**
     * 是否有前一页
     */
    private boolean hasPrevious = false;

    /**
     * 是否有下一页
     */
    private boolean hasNext = false;

    /**
     * 是否有尾页
     */
    private boolean hasLast = false;

    /**
     * 总页数
     */
    private int totalPages = 1;

    /**
     * 下一页号码
     */
    private int nextPage = 1;

    /**
     * 上一页号码
     */
    private int previousPage = 1;

    /**
     * 默认构造方法，请使用覆盖的构造方法
     */
    public Pagination()
    {

    }

    /**
     * 构造分页对象方法
     * 
     * @param rowSet
     * @param rowSize
     * @param pageIndex
     * @param pageSize
     */
    public Pagination(Collection rowSet, long rowSize, int pageIndex,
	    int pageSize)
    {
	this.rowSet = rowSet;
	this.rowSize = rowSize;
	this.pageIndex = pageIndex;
	this.pageSize = pageSize;
    }

    /**
     * 返回总页数
     * 
     * @return long
     */
    public long getTotalPages()
    {
	return totalPages;
    }

    /**
     * 设置总页数
     * 
     * @param totalPages
     */
    public void setTotalPages(int totalPages)
    {
	this.totalPages = totalPages;
    }

    public int getPageIndex()
    {
	return pageIndex;
    }

    /**
     * 设置第几页
     * 
     * @param pageIndex
     */
    public void setPageIndex(int pageIndex)
    {
	this.pageIndex = pageIndex;
    }

    /**
     * 获得该页记录集大小
     *  
     * @return int
     */
    public int getPageSize()
    {
	return pageSize;
    }

    /**
     * 设置该页记录集大小
     * 
     * @param pageSize
     */
    public void setPageSize(int pageSize)
    {
	this.pageSize = pageSize;
    }

    /**
     * 获得本页记录集
     * 
     * @return Collection
     */
    public Collection getRowSet()
    {
	return rowSet;
    }

    /**
     * 设置本页记录集
     * 
     * @param rowSet
     */
    public void setRowSet(Collection rowSet)
    {
	this.rowSet = rowSet;
    }

    /**
     * 获得总记录数
     * 
     * @return long
     */
    public long getRowSize()
    {
	return rowSize;
    }

    /**
     * 设置总记录数
     * 
     * @param rowSize
     */
    public void setRowSize(long rowSize)
    {
	this.rowSize = rowSize;
    }

    /**
     * 是否有第一页
     * 
     * @return boolean
     */
    public boolean isHasFirst()
    {
	return hasFirst;
    }

    /**
     * 设置有第一页
     * 
     * @param hasFirst
     */
    public void setHasFirst(boolean hasFirst)
    {
	this.hasFirst = hasFirst;
    }

    /**
     * 是否有最后一页
     * 
     * @return boolean
     */
    public boolean isHasLast()
    {
	return hasLast;
    }

    /**
     * 设置有最后一页
     * 
     * @param hasLast
     */
    public void setHasLast(boolean hasLast)
    {
	this.hasLast = hasLast;
    }

    /**
     * 是否有下一页
     * 
     * @return boolean
     */
    public boolean isHasNext()
    {
	return hasNext;
    }

    /**
     * 设置有下一页面
     * 
     * @param hasNext
     */
    public void setHasNext(boolean hasNext)
    {
	this.hasNext = hasNext;
    }

    /**
     * 是否有上一页
     * 
     * @return boolean
     */
    public boolean isHasPrevious()
    {
	return hasPrevious;
    }

    /**
     * 设置有上一页
     * 
     * @param hasPrevious
     */
    public void setHasPrevious(boolean hasPrevious)
    {
	this.hasPrevious = hasPrevious;
    }

    /**
     * 获得下一页页码
     * 
     * @return int
     */
    public int getNextPage()
    {
	return nextPage;
    }

    /**
     * 设置上一页页码
     * 
     * @param nextPage
     */
    public void setNextPage(int nextPage)
    {
	this.nextPage = nextPage;
    }

    /**
     * 获得上一页页码
     * 
     * @return int
     */
    public int getPreviousPage()
    {
	return previousPage;
    }

    /**
     * 设置上一页页码
     * 
     * @param previousPage
     */
    public void setPreviousPage(int previousPage)
    {
	this.previousPage = previousPage;
    }

    /**
     * 建立分页信息
     */
    public void buildPagination()
    {
	// 计算总页数
	if (rowSize % pageSize > 0)
	{
	    setTotalPages((int) (rowSize / pageSize + 1));
	} else
	{
	    if (rowSize > 0)
		this.setTotalPages((int) (rowSize / pageSize));
	}
	// 是否有上一页，上一页号码，是否有首页
	if (pageIndex > 1)
	{
	    hasFirst = true;
	    hasPrevious = true;
	    previousPage = pageIndex - 1;
	} else
	{
	    previousPage = 1;
	}
	if (pageIndex < totalPages)
	{
	    hasLast = true;
	    hasNext = true;
	    nextPage = pageIndex + 1;
	} else
	{
	    nextPage = totalPages;
	}
    }
}
