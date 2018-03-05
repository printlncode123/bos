package com.itheima.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IDecidedzoneDAO;
import com.itheima.bos.dao.ISubareaDAO;
import com.itheima.bos.dao.impl.SubareaDAOImpl;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.IDecidedzoneService;
import com.itheima.bos.utils.PageBean;

@Service
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService{
	@Autowired
	private IDecidedzoneDAO decidedzoneDAOImpl;
	@Autowired
	private ISubareaDAO SubareaDAOImpl;
	@Override
	public void save(Decidedzone model, String[] subareaid) {
		decidedzoneDAOImpl.save(model);//添加定区
		for (String id : subareaid) {
			Subarea subarea = SubareaDAOImpl.findById(id);//根据分区id找到分区对象
			subarea.setDecidedzone(model);//因为定区将主控权交给了分区所以这里要用分区关联定区
		}
		
	}
	@Override
	public void pageQuery(PageBean pageBean) {
		decidedzoneDAOImpl.pageQuery(pageBean);
		
	}

}
