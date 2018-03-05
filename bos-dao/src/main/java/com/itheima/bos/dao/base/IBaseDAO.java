package com.itheima.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.utils.PageBean;

/**�־ò�ͨ�ýӿ�*/
public interface IBaseDAO<T> {
	public void save(T entity);
	public void delete(T entity);
	public void update(T entity);
	public T findById(Serializable id);
	public List<T> findAll();
	//ͨ�õĸ��·���
	public void executeUpdate(String queryName,Object...objects);
	//ͨ�õķ�ҳ��ѯ����
	public void pageQuery(PageBean pageBean);
	public void saveorupdate(T entity);
	//ͨ�õĲ�ѯ����
	public List<T> findByDetachedCriteria(DetachedCriteria detachedCriteria);
}
