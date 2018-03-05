package com.itheima.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.utils.PageBean;

/**持久层通用接口*/
public interface IBaseDAO<T> {
	public void save(T entity);
	public void delete(T entity);
	public void update(T entity);
	public T findById(Serializable id);
	public List<T> findAll();
	//通用的更新方法
	public void executeUpdate(String queryName,Object...objects);
	//通用的分页查询方法
	public void pageQuery(PageBean pageBean);
	public void saveorupdate(T entity);
	//通用的查询方法
	public List<T> findByDetachedCriteria(DetachedCriteria detachedCriteria);
}
