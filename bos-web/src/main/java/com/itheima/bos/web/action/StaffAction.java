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
	 * 查询未删除的取派员
	 * @return
	 */
	public String listajax(){
		List<Staff> staffsnotdel=staffServiceImpl.findNotDel();
		java2JsonArrayString(staffsnotdel, new String[]{"decidedzones"});
		return NONE;
	}
	//添加取派员
	public String save(){
		staffServiceImpl.save(model);
		return "list";
	}
	
	//接收前台页面传递的分页参数
	int page;//jquery-easyui中的当前页
	int rows;//jquery-easyui中的每页显示的记录数
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
	//分页查询取派员
	public String pageQuery() throws IOException{
		PageBean pageBean=new PageBean();
		pageBean.setCurrentPage(page);//将接收的当前页参数设置到pageBean中
		pageBean.setPageSize(rows);//将接收的每页显示的记录数参数设置到pageBean
		//获取staff的查询条件对象
		DetachedCriteria staffCriteria = DetachedCriteria.forClass(Staff.class);
		pageBean.setDetachedCriteria(staffCriteria);//将查询条件设置到pageBean
		staffServiceImpl.pageQuery(pageBean);
		
		//通过流的方式使用json将pageBean响应到浏览器而不是跳转
		//JSONObject==>将单一对象转为json
		//JSONArray==>将数组集合转为json
		//通过jsonConfig.setExcludes(new String[]{"","",""...});设置不需要响应到客户端的数据
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setExcludes(new String[]{"currentPage","detachedCriteria","pageSize","decidedzones"});
		String json = JSONObject.fromObject(pageBean,jsonConfig).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(json);
		return NONE;
	}
	
	//接收前台页面传递的参数ids便于批量删除
	String ids;
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	//取派员的逻辑批量删除（其实是将deltag从0更新为1）
	@RequiresPermissions("staff-delete")//表示执行该方法需要用户具有staff-delete的权限
	public String deleteBatch(){
		staffServiceImpl.deleteBatch(ids);
		return "list";
	}
	
	//根据id修改取派员信息
	@RequiresPermissions("staff-edit")
	public String edit(){
		//获取要修改的取派员的id
		String id = model.getId();
		System.out.println("id:"+id);
		//根据id获取要修改的取派员
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
