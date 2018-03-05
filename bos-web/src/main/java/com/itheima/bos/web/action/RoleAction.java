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
	private IRoleService roleServiceImpl;//ע����springioc����������roleSerivce
	private String functionIds;//��������ǰ̨ҳ�洫�ݵ�Ȩ��id�� 
	//�����յĲ�����ֵ��ֵջ����action�е�����
	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}
	/*//��ҳ���ȡactionֵջ������ֵ��ʾ��jspҳ��ʱ��Ҫget����
	public String getFunctionIds() {
		return functionIds;
	}*/

	
	/**
	 * ��ӽ�ɫ������Ȩ��
	 */
	public String add(){
		roleServiceImpl.save(model,functionIds);
		return "list";
	}
	 
	/**
	 *��ҳ��ѯ 
	 */
	public String pageQuery(){
		roleServiceImpl.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"functions","users"});
	 	return NONE;
	}
	
	/**
	 * ��ѯ���еĽ�ɫ 
	 */
	public String findAll(){
		List<Role> roles=roleServiceImpl.findAll();
		this.java2JsonArrayString(roles, new String[]{"functions","users"});
		return NONE;
				
	}
}
