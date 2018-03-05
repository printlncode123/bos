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
		decidedzoneDAOImpl.save(model);//��Ӷ���
		for (String id : subareaid) {
			Subarea subarea = SubareaDAOImpl.findById(id);//���ݷ���id�ҵ���������
			subarea.setDecidedzone(model);//��Ϊ����������Ȩ�����˷�����������Ҫ�÷�����������
		}
		
	}
	@Override
	public void pageQuery(PageBean pageBean) {
		decidedzoneDAOImpl.pageQuery(pageBean);
		
	}

}
