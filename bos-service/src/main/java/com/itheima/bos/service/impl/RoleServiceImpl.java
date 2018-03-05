package com.itheima.bos.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IRoleDAO;
import com.itheima.bos.domain.Auth;
import com.itheima.bos.domain.Role;
import com.itheima.bos.service.IRoleService;
import com.itheima.bos.utils.PageBean;
@Service//��ʶ����,ҵ����������pojoʵ������spring��������
@Transactional//��spring�����������������
public class RoleServiceImpl implements IRoleService{
	@Autowired
	private IRoleDAO roleDAOImpl;
	@Override
	public void save(Role model, String functionIds) {
		roleDAOImpl.save(model);//����ɫ�־û������ݿ��ɫ����
		if (StringUtils.isNotBlank(functionIds)) {
			String[] functions = functionIds.split(",");
			for (String functionId : functions) {
				Auth function=new Auth();
				function.setId(functionId);
				model.getFunctions().add(function);//�־ö�������йܶ���
			}
			
		}
	
	}
	@Override
	public void pageQuery(PageBean pageBean) {
		roleDAOImpl.pageQuery(pageBean);
		
	}
	@Override
	public List<Role> findAll() {
		return roleDAOImpl.findAll();
	}

}
