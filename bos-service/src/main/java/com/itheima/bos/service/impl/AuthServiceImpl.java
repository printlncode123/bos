package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IAuthDAO;
import com.itheima.bos.domain.Auth;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.IAuthService;
import com.itheima.bos.utils.BosUtils;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class AuthServiceImpl implements IAuthService {
	@Autowired
	private IAuthDAO authDAOImpl;
	@Override
	public List<Auth> findParentAuth() {
		return authDAOImpl.findAll();
	}
	@Override
	public void save(Auth model) {
		Auth parentFunction = model.getParentFunction();
		if (parentFunction!=null&&parentFunction.getId().equals("")) {
			model.setParentFunction(null);
		}
		authDAOImpl.save(model);
	}
	@Override
	public void pageQuery(PageBean pageBean) {
		authDAOImpl.pageQuery(pageBean);
		
	}
	@Override
	public List<Auth> findMenu() {
		List<Auth> auths=null;
		User user = (User) BosUtils.getValue1("loginUser");
		if (user.getUsername().equals("admin")) {
			auths= authDAOImpl.findAllMenu();
		}else{
		auths= authDAOImpl.findMenuByUserId(user.getId());
		}
		return auths;
	}

}
