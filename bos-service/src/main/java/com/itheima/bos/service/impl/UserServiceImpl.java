package com.itheima.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IUserDAO;
import com.itheima.bos.domain.Role;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.IUserService;
import com.itheima.bos.utils.MD5Utils;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class UserServiceImpl implements IUserService{
	@Autowired
	private IUserDAO userDAO;
	@Override
	public User login(User model) {
		return userDAO.findUserByNameAndPass(model.getUsername(),MD5Utils.md5(model.getPassword()));
	}
	@Override
	public void editPassword(String id, String password) {
		userDAO.executeUpdate("user.editPassword",MD5Utils.md5(password),id);//user.editPassword����User.hbm.xml�����õ�query��ǩ��name����
		}
	
	//�����û������ҹ�����ɫ
	public void save(User model, String[] roleIds) {
		String md5 = MD5Utils.md5(model.getPassword());
		model.setPassword(md5);//������ʹ��md5����
		userDAO.save(model);//���������û�����־û������ݿ�
		if (roleIds!=null&&roleIds.length>0) {
			for (String roleId : roleIds) {
				Role role=new Role(roleId);
				model.getRoles().add(role);//���û���������ѡ��Ȩ��
			}
		}
	}
	@Override
	public void pageQuery(PageBean pageBean) {
		userDAO.pageQuery(pageBean);
		
	}

}
