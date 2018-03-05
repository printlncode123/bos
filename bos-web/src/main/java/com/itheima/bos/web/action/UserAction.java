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
@Scope("prototype")//多例，tomcat启动时不会创建action实例，当需要使用时才创建；不写表示单例，tomcat启动时默认创建一个action实例
public class UserAction extends BaseAction<User> {
	private static final long serialVersionUID = 1L;
	private String checkCode;//接受前台页面输入的传过来的验证码参数
	@Autowired
	private IUserService userService;
	@Autowired
	private ICustomer proxy;//注入通过spring创建的代理对象
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	
	/**使用shiro框架用户登陆*/
	public String login(){
		//System.out.println(proxy.findAll());
		//获取保存在session中的key验证码的值，与用户输入的值做比对
		String validateCode=(String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if (StringUtils.isNotBlank(checkCode)&& checkCode.equals(validateCode)) {//StringUtils.isNotBlank(checkCode)判断指定字符串是否不为空
			//通过shiro框架提供的securityutils获取当前用户
			Subject subject = SecurityUtils.getSubject();//状态：未认证
			//认证令牌
			AuthenticationToken token= new UsernamePasswordToken(model.getUsername(), MD5Utils.md5(model.getPassword()));	
		try{
			//subject调用login()内部的securitymanager，securitymanager调用realm（相当于dao）
			subject.login(token);
			
			}catch (Exception e) {
				e.printStackTrace();
				return LOGIN;
			}
		//获取用户信息
		User user = (User) subject.getPrincipal();
		//将用户保存到session域中
		ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
		return "home";
		}else{//如果为空或者输入的不比匹配，回显信息且到登录页面
			this.addActionError("输入的验证码不匹配");
			return LOGIN;
		}
	} 

	/**用户登陆*/
	public String loginOld(){
		//System.out.println(proxy.findAll());
		//获取保存在session中的key验证码的值，与用户输入的值做比对
		String validateCode=(String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if (StringUtils.isNotBlank(checkCode)&& checkCode.equals(validateCode)) {//StringUtils.isNotBlank(checkCode)判断指定字符串是否不为空
			User user=userService.login(model);
			if (user!=null) {
				session.put("loginUser", user);
				/*ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);*/
				return "home";
			}else{
				this.addActionError("用户名或密码输入错误");
				return LOGIN;
			}
		}else{//如果为空或者输入的不比匹配，回显信息且到登录页面
			this.addActionError("输入的验证码不匹配");
			return LOGIN;
		}
	} 
	
	/**用ru户注销*/
	public String logout(){
		/*ServletActionContext.getRequest().getSession().invalidate();*/
		session.remove("loginUser");
		return LOGIN;
	} 
	
	/**修改密码*/
	public String editPassword()throws Exception{
		//获取当前session中用户
		User user=(User) BosUtils.getValue1("loginUser");
		String f="1";
		//根据当前用户id修改密码
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
		return NONE;//发送的是ajax请求不需要跳转，直接将数据响应到浏览器客户端
	}
	
	/**
	 * 添加用户
	 * @return
	 */
	private String[] roleIds;
	//将接收的参数赋值给值栈顶部action的属性
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	public String add(){
		userService.save(model,roleIds);
		return "list";
	}
	/**
	 * 分页查询用户
	 * @return
	 */
	public String pageQuery(){
		userService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"noticebills","roles"});
		return NONE;
	}
}
