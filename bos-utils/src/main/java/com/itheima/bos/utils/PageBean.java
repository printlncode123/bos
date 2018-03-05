package com.itheima.bos.utils;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

/**
 * 分页封装分页属性
 *	currentPage:当前页
 *	pageSize:每页显示的记录数
 *	total:总记录数
 *	rows:每页显示的记录
 *  detachedCriteria：离线查询条件
 */
public class PageBean {
	private int currentPage;
	private int pageSize;
	private int total;
	private List rows;//每页显示的记录
	private DetachedCriteria detachedCriteria;//离线查询条件
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	public DetachedCriteria getDetachedCriteria() {
		return detachedCriteria;
	}
	public void setDetachedCriteria(DetachedCriteria detachedCriteria) {
		this.detachedCriteria = detachedCriteria;
	}

	
}