package com.itheima.bos.dao;

import com.itheima.bos.dao.base.IBaseDAO;
import com.itheima.bos.domain.User;

public interface IUserDAO extends IBaseDAO<User> {

	public User findUserByNameAndPass(String name,String pass);

	public User findUserByName(String username);

}
