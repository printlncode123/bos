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
		userDAO.executeUpdate("user.editPassword",MD5Utils.md5(password),id);//user.editPassword是在User.hbm.xml中设置的query标签的name属性
		}
	
	//新增用户，并且关联角色
	public void save(User model, String[] roleIds) {
		String md5 = MD5Utils.md5(model.getPassword());
		model.setPassword(md5);//将密码使用md5加密
		userDAO.save(model);//将新增的用户对象持久化到数据库
		if (roleIds!=null&&roleIds.length>0) {
			for (String roleId : roleIds) {
				Role role=new Role(roleId);
				model.getRoles().add(role);//将用户关联到所选的权限
			}
		}
	}
	@Override
	public void pageQuery(PageBean pageBean) {
		userDAO.pageQuery(pageBean);
		
	}

}
