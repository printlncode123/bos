package com.itheima.bos.dao;

import java.util.List;

import com.itheima.bos.dao.base.IBaseDAO;
import com.itheima.bos.domain.Subarea;

public interface ISubareaDAO extends IBaseDAO<Subarea>{

	public List<Object> findSubareasGroupByProvince();

}
