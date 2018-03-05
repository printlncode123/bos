package com.itheima.bos.dao;

import java.util.List;

import com.itheima.bos.dao.base.IBaseDAO;
import com.itheima.bos.domain.Auth;

public interface IAuthDAO extends IBaseDAO<Auth>{

	public List<Auth> findAuthByUserId(String id);

	public List<Auth> findAllMenu();

	public List<Auth> findMenuByUserId(String id);

}
