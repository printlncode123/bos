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
	//��֤����
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("�Զ����realm����֤����ִ���ˡ�������");
		UsernamePasswordToken passwordToken = (UsernamePasswordToken)token;
		//���ҳ��������û���
		String username = passwordToken.getUsername();
		//�����û�����ѯ���ݿ��е�����
		User user = userDAOImpl.findUserByName(username);
		if(user == null){
			//ҳ��������û���������
			return null;
		}
		//����֤��Ϣ����
		AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
		//��ܸ���ȶ����ݿ��е������ҳ������������Ƿ�һ��
		return info;
	}

	//��Ȩ����
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		//��ȡ��ǰ�û�
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		List<Auth> list=null;
		if (user.getUsername().equals("admin")) {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Auth.class);
			list=authDAOImpl.findByDetachedCriteria(detachedCriteria);
		}else{
			list=authDAOImpl.findAuthByUserId(user.getId());
		}
		for (Auth auth : list) {
			//Ϊ��ǰ�û���Ȩ
			info.addStringPermission(auth.getCode());
		}
		/*//Ϊ��ǰ�û���Ȩ
		info.addStringPermission("staff-list");*/
		return info;
	}

}
