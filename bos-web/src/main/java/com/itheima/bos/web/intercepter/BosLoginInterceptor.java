package com.itheima.bos.web.intercepter;

import com.itheima.bos.domain.User;
import com.itheima.bos.utils.BosUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
//�Զ������������û�δ��¼�Զ�������¼ҳ
public class BosLoginInterceptor extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		ActionProxy proxy = invocation.getProxy();
		Object action = proxy.getAction();
		String actionName = proxy.getActionName();
		String namespace = proxy.getNamespace();
		System.out.println(namespace+actionName);
		//��ȡmap��session���е�loginUser
		User user=(User) BosUtils.getValue1("loginUser");
		if (user==null) {
			return "login";
		}
		return invocation.invoke();//����
	}

}
