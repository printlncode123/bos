package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.apache.xml.resolver.helpers.PublicId;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.IStaffService;
import com.itheima.bos.utils.PageBean;
import com.itheima.bos.web.action.base.BaseAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>{
	@Autowired
	private IStaffService staffServiceImpl;
	
	/**
	 * ��ѯδɾ����ȡ��Ա
	 * @return
	 */
	public String listajax(){
		List<Staff> staffsnotdel=staffServiceImpl.findNotDel();
		java2JsonArrayString(staffsnotdel, new String[]{"decidedzones"});
		return NONE;
	}
	//���ȡ��Ա
	public String save(){
		staffServiceImpl.save(model);
		return "list";
	}
	
	//����ǰ̨ҳ�洫�ݵķ�ҳ����
	int page;//jquery-easyui�еĵ�ǰҳ
	int rows;//jquery-easyui�е�ÿҳ��ʾ�ļ�¼��
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	//��ҳ��ѯȡ��Ա
	public String pageQuery() throws IOException{
		PageBean pageBean=new PageBean();
		pageBean.setCurrentPage(page);//�����յĵ�ǰҳ�������õ�pageBean��
		pageBean.setPageSize(rows);//�����յ�ÿҳ��ʾ�ļ�¼���������õ�pageBean
		//��ȡstaff�Ĳ�ѯ��������
		DetachedCriteria staffCriteria = DetachedCriteria.forClass(Staff.class);
		pageBean.setDetachedCriteria(staffCriteria);//����ѯ�������õ�pageBean
		staffServiceImpl.pageQuery(pageBean);
		
		//ͨ�����ķ�ʽʹ��json��pageBean��Ӧ���������������ת
		//JSONObject==>����һ����תΪjson
		//JSONArray==>�����鼯��תΪjson
		//ͨ��jsonConfig.setExcludes(new String[]{"","",""...});���ò���Ҫ��Ӧ���ͻ��˵�����
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setExcludes(new String[]{"currentPage","detachedCriteria","pageSize","decidedzones"});
		String json = JSONObject.fromObject(pageBean,jsonConfig).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(json);
		return NONE;
	}
	
	//����ǰ̨ҳ�洫�ݵĲ���ids��������ɾ��
	String ids;
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	//ȡ��Ա���߼�����ɾ������ʵ�ǽ�deltag��0����Ϊ1��
	@RequiresPermissions("staff-delete")//��ʾִ�и÷�����Ҫ�û�����staff-delete��Ȩ��
	public String deleteBatch(){
		staffServiceImpl.deleteBatch(ids);
		return "list";
	}
	
	//����id�޸�ȡ��Ա��Ϣ
	@RequiresPermissions("staff-edit")
	public String edit(){
		//��ȡҪ�޸ĵ�ȡ��Ա��id
		String id = model.getId();
		System.out.println("id:"+id);
		//����id��ȡҪ�޸ĵ�ȡ��Ա
		Staff staff = staffServiceImpl.findById(id);
		System.out.println(staff);
		staff.setHaspda(model.getHaspda());
		staff.setName(model.getName());
		staff.setStandard(model.getStandard());
		staff.setStation(model.getStation());
		staff.setTelephone(model.getTelephone());
		staffServiceImpl.update(staff);
		return "list";
	}
}
