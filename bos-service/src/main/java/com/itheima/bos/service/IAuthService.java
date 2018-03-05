package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Auth;
import com.itheima.bos.utils.PageBean;

public interface IAuthService {

	public List<Auth> findParentAuth();

	public void save(Auth model);

	public void pageQuery(PageBean pageBean);

	public List<Auth> findMenu();

}
