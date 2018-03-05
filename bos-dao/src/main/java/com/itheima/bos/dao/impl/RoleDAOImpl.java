package com.itheima.bos.dao.impl;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IRoleDAO;
import com.itheima.bos.dao.base.impl.BaseDAOImpl;
import com.itheima.bos.domain.Role;
@Repository//标识作用，dao层组件
public class RoleDAOImpl extends BaseDAOImpl<Role> implements IRoleDAO{

}
