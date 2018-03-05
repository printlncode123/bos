package com.itheima.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Auth;
import com.itheima.bos.service.IAuthService;
import com.itheima.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class AuthAction extends BaseAction<Auth> {
	@Autowired
	private IAuthService authServiceImpl;
	public String listajax(){
		List<Auth> auths=authServiceImpl.findParentAuth();
		this.java2JsonArrayString(auths, new String[]{"parentFunction","roles"});
		return NONE;
	}
	
	//添加权限
	public String add(){
		authServiceImpl.save(model);
		return "list";
	}
	
	//分页查询
	public String pageQuery(){
		String page = model.getPage();//页面传递的page(当前页)值会被model的page属性接收，其实是要设置为pageBean的当前页，所以要取出来在赋值给pageBean的currentPage属性
		pageBean.setCurrentPage(Integer.parseInt(page));
		authServiceImpl.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"parentFunction","roles","children"});
		return NONE;
	}
	
	//查询菜单项
	public String findMenu(){
		List<Auth> auths=authServiceImpl.findMenu();
		this.java2JsonArrayString(auths, new String[]{"roles","children","parentFunction"});
		return NONE;
	}
}
