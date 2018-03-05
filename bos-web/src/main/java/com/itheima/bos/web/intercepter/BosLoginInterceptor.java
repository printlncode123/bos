package com.itheima.bos.web.intercepter;

import com.itheima.bos.domain.User;
import com.itheima.bos.utils.BosUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
//自定义拦截器，用户未登录自动跳到登录页
public class BosLoginInterceptor extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		ActionProxy proxy = invocation.getProxy();
		Object action = proxy.getAction();
		String actionName = proxy.getActionName();
		String namespace = proxy.getNamespace();
		System.out.println(namespace+actionName);
		//获取map的session域中的loginUser
		User user=(User) BosUtils.getValue1("loginUser");
		if (user==null) {
			return "login";
		}
		return invocation.invoke();//放行
	}

}
