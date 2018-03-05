package com.itheima.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IAuthDAO;
import com.itheima.bos.dao.base.impl.BaseDAOImpl;
import com.itheima.bos.domain.Auth;
@Repository
public class AuthDAOImpl extends BaseDAOImpl<Auth> implements IAuthDAO{

	@Override
	public List<Auth> findAll() {
		String hql = "FROM Auth f WHERE f.parentFunction IS NULL";//��ѯ���еĸ��ڵ㣬����ȥ���������ӽڵ�(lazy='false')
		return (List<Auth>) this.getHibernateTemplate().find(hql);
	}

	@Override
	public List<Auth> findAuthByUserId(String id) {
		//ȥ���ݿ��ѯ���û�����Ȩ��
		String hql="select DISTINCT a FROM Auth a LEFT OUTER JOIN a.roles r LEFT OUTER JOIN r.users u WHERE u.id=?";
		List<Auth> auth = (List<Auth>) this.getHibernateTemplate().find(hql, id);
		return auth;
	}

	//��ѯ���в˵�
	@Override
	public List<Auth> findAllMenu() {
		String hql="FROM Auth a WHERE a.generatemenu='1' order by a.zindex desc";
		List<Auth> auths = (List<Auth>) this.getHibernateTemplate().find(hql);
		return auths;
	}

	@Override
	public List<Auth> findMenuByUserId(String id) {
		String hql="SELECT distinct a FROM Auth a LEFT OUTER JOIN a.roles r LEFT OUTER JOIN r.users u WHERE u.id=? and a.generatemenu='1' order by a.zindex desc";
	    return (List<Auth>) this.getHibernateTemplate().find(hql, id);
	}
	
}
