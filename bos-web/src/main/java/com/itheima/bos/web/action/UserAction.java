package com.itheima.bos.web.action;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.IUserService;
import com.itheima.bos.utils.BosUtils;
import com.itheima.bos.utils.MD5Utils;
import com.itheima.bos.web.action.base.BaseAction;
import com.itheima.crm.service.ICustomer;
@Controller
@Scope("prototype")//������tomcat����ʱ���ᴴ��actionʵ��������Ҫʹ��ʱ�Ŵ�������д��ʾ������tomcat����ʱĬ�ϴ���һ��actionʵ��
public class UserAction extends BaseAction<User> {
	private static final long serialVersionUID = 1L;
	private String checkCode;//����ǰ̨ҳ������Ĵ���������֤�����
	@Autowired
	private IUserService userService;
	@Autowired
	private ICustomer proxy;//ע��ͨ��spring�����Ĵ������
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	
	/**ʹ��shiro����û���½*/
	public String login(){
		//System.out.println(proxy.findAll());
		//��ȡ������session�е�key��֤���ֵ�����û������ֵ���ȶ�
		String validateCode=(String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if (StringUtils.isNotBlank(checkCode)&& checkCode.equals(validateCode)) {//StringUtils.isNotBlank(checkCode)�ж�ָ���ַ����Ƿ�Ϊ��
			//ͨ��shiro����ṩ��securityutils��ȡ��ǰ�û�
			Subject subject = SecurityUtils.getSubject();//״̬��δ��֤
			//��֤����
			AuthenticationToken token= new UsernamePasswordToken(model.getUsername(), MD5Utils.md5(model.getPassword()));	
		try{
			//subject����login()�ڲ���securitymanager��securitymanager����realm���൱��dao��
			subject.login(token);
			
			}catch (Exception e) {
				e.printStackTrace();
				return LOGIN;
			}
		//��ȡ�û���Ϣ
		User user = (User) subject.getPrincipal();
		//���û����浽session����
		ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
		return "home";
		}else{//���Ϊ�ջ�������Ĳ���ƥ�䣬������Ϣ�ҵ���¼ҳ��
			this.addActionError("�������֤�벻ƥ��");
			return LOGIN;
		}
	} 

	/**�û���½*/
	public String loginOld(){
		//System.out.println(proxy.findAll());
		//��ȡ������session�е�key��֤���ֵ�����û������ֵ���ȶ�
		String validateCode=(String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if (StringUtils.isNotBlank(checkCode)&& checkCode.equals(validateCode)) {//StringUtils.isNotBlank(checkCode)�ж�ָ���ַ����Ƿ�Ϊ��
			User user=userService.login(model);
			if (user!=null) {
				session.put("loginUser", user);
				/*ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);*/
				return "home";
			}else{
				this.addActionError("�û����������������");
				return LOGIN;
			}
		}else{//���Ϊ�ջ�������Ĳ���ƥ�䣬������Ϣ�ҵ���¼ҳ��
			this.addActionError("�������֤�벻ƥ��");
			return LOGIN;
		}
	} 
	
	/**��ru��ע��*/
	public String logout(){
		/*ServletActionContext.getRequest().getSession().invalidate();*/
		session.remove("loginUser");
		return LOGIN;
	} 
	
	/**�޸�����*/
	public String editPassword()throws Exception{
		//��ȡ��ǰsession���û�
		User user=(User) BosUtils.getValue1("loginUser");
		String f="1";
		//���ݵ�ǰ�û�id�޸�����
			try{
				userService.editPassword(user.getId(),model.getPassword());
			}catch (Exception e) {
				f="0";
				e.printStackTrace();
			}
		try {
			ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
			ServletActionContext.getResponse().getWriter().print(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;//���͵���ajax������Ҫ��ת��ֱ�ӽ�������Ӧ��������ͻ���
	}
	
	/**
	 * ����û�
	 * @return
	 */
	private String[] roleIds;
	//�����յĲ�����ֵ��ֵջ����action������
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	public String add(){
		userService.save(model,roleIds);
		return "list";
	}
	/**
	 * ��ҳ��ѯ�û�
	 * @return
	 */
	public String pageQuery(){
		userService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"noticebills","roles"});
		return NONE;
	}
}
