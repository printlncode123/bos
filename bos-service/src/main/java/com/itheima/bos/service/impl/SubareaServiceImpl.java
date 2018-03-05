package com.itheima.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.ISubareaDAO;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.ISubareaService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class SubareaServiceImpl implements ISubareaService {
	@Autowired
	private ISubareaDAO subareaDAOImpl;
	@Override
	public void save(Subarea subarea) {
		subareaDAOImpl.save(subarea);

	}
	@Override
	public void pageQuery(PageBean pageBean) {
		subareaDAOImpl.pageQuery(pageBean);
		
	}
	@Override
	public List<Subarea> findAll() {
		return subareaDAOImpl.findAll();
	}
	@Override
	public List<Subarea> findNotAssociation() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		detachedCriteria.add(Restrictions.isNull("decidedzone"));//添加过滤条件()
		List<Subarea> subareas = subareaDAOImpl.findByDetachedCriteria(detachedCriteria);
		return subareas;
	}
	@Override
	public List<Subarea> findListByDecidedzoneId(String decidedzoneId) {
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Subarea.class);
		detachedCriteria.add(Restrictions.eq("decidedzone.id", decidedzoneId));//添加查询条件
		List<Subarea> subList = subareaDAOImpl.findByDetachedCriteria(detachedCriteria);//根据条件查询
		return subList;
	}
	@Override
	public List<Object> findSubareasGroupByProvince() {
		List<Object> list=subareaDAOImpl.findSubareasGroupByProvince();
		return list;
	}

}
