package com.itheima.bos.service.realm;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.itheima.bos.dao.IAuthDAO;
import com.itheima.bos.dao.IUserDAO;
import com.itheima.bos.domain.Auth;
import com.itheima.bos.domain.User;

public class BosRealm extends AuthorizingRealm{
	@Autowired
	private IUserDAO userDAOImpl;
	@Autowired
	private IAuthDAO authDAOImpl;
	//认证方法
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("自定义的realm中认证方法执行了。。。。");
		UsernamePasswordToken passwordToken = (UsernamePasswordToken)token;
		//获得页面输入的用户名
		String username = passwordToken.getUsername();
		//根据用户名查询数据库中的密码
		User user = userDAOImpl.findUserByName(username);
		if(user == null){
			//页面输入的用户名不存在
			return null;
		}
		//简单认证信息对象
		AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
		//框架负责比对数据库中的密码和页面输入的密码是否一致
		return info;
	}

	//授权方法
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		//获取当前用户
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		List<Auth> list=null;
		if (user.getUsername().equals("admin")) {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Auth.class);
			list=authDAOImpl.findByDetachedCriteria(detachedCriteria);
		}else{
			list=authDAOImpl.findAuthByUserId(user.getId());
		}
		for (Auth auth : list) {
			//为当前用户授权
			info.addStringPermission(auth.getCode());
		}
		/*//为当前用户授权
		info.addStringPermission("staff-list");*/
		return info;
	}

}
