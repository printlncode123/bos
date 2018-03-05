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
	
	//���Ȩ��
	public String add(){
		authServiceImpl.save(model);
		return "list";
	}
	
	//��ҳ��ѯ
	public String pageQuery(){
		String page = model.getPage();//ҳ�洫�ݵ�page(��ǰҳ)ֵ�ᱻmodel��page���Խ��գ���ʵ��Ҫ����ΪpageBean�ĵ�ǰҳ������Ҫȡ�����ڸ�ֵ��pageBean��currentPage����
		pageBean.setCurrentPage(Integer.parseInt(page));
		authServiceImpl.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"parentFunction","roles","children"});
		return NONE;
	}
	
	//��ѯ�˵���
	public String findMenu(){
		List<Auth> auths=authServiceImpl.findMenu();
		this.java2JsonArrayString(auths, new String[]{"roles","children","parentFunction"});
		return NONE;
	}
}
