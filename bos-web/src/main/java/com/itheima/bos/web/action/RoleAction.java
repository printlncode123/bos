package com.itheima.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Role;
import com.itheima.bos.service.IRoleService;
import com.itheima.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{
	@Autowired
	private IRoleService roleServiceImpl;//注入由springioc容器创建的roleSerivce
	private String functionIds;//用来接收前台页面传递的权限id串 
	//将接收的参数赋值给值栈顶部action中的属性
	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}
	/*//在页面获取action值栈中属性值显示到jsp页面时需要get方法
	public String getFunctionIds() {
		return functionIds;
	}*/

	
	/**
	 * 添加角色并关联权限
	 */
	public String add(){
		roleServiceImpl.save(model,functionIds);
		return "list";
	}
	 
	/**
	 *分页查询 
	 */
	public String pageQuery(){
		roleServiceImpl.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"functions","users"});
	 	return NONE;
	}
	
	/**
	 * 查询所有的角色 
	 */
	public String findAll(){
		List<Role> roles=roleServiceImpl.findAll();
		this.java2JsonArrayString(roles, new String[]{"functions","users"});
		return NONE;
				
	}
}
