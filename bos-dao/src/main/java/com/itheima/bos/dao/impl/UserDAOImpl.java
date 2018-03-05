package com.itheima.bos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IUserDAO;
import com.itheima.bos.dao.base.impl.BaseDAOImpl;
import com.itheima.bos.domain.User;
import com.itheima.bos.utils.MD5Utils;
@Repository
public class UserDAOImpl extends BaseDAOImpl<User> implements IUserDAO {

	@Override
	public User findUserByNameAndPass(String username,String password) {
		String hql="FROM User u where u.username=? and u.password=?";
		List<User> uList = (List<User>) this.getHibernateTemplate().find(hql, username,password);
		if (uList!=null&&uList.size()>0) {
			return uList.get(0);
		}
		return null;
	}

	@Override
	public User findUserByName(String username) {
		String hql="FROM User u where u.username=?";
		List<User> user = (List<User>) this.getHibernateTemplate().find(hql, username);
		if (user!=null) {
			return user.get(0);
		}
		return null;
	}

}
