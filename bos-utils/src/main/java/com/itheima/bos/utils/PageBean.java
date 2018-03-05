package com.itheima.bos.utils;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

/**
 * ��ҳ��װ��ҳ����
 *	currentPage:��ǰҳ
 *	pageSize:ÿҳ��ʾ�ļ�¼��
 *	total:�ܼ�¼��
 *	rows:ÿҳ��ʾ�ļ�¼
 *  detachedCriteria�����߲�ѯ����
 */
public class PageBean {
	private int currentPage;
	private int pageSize;
	private int total;
	private List rows;//ÿҳ��ʾ�ļ�¼
	private DetachedCriteria detachedCriteria;//���߲�ѯ����
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