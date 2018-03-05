package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Subarea;
import com.itheima.bos.utils.PageBean;

public interface ISubareaService {
	public void save(Subarea subarea);

	public void pageQuery(PageBean pageBean);

	public List<Subarea> findAll();

	public List<Subarea> findNotAssociation();

	public List<Subarea> findListByDecidedzoneId(String decidedzoneId);

	public List<Object> findSubareasGroupByProvince();
}
