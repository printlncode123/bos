package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.domain.Region;
import com.itheima.bos.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 表现层代码的抽取
 *
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T>,SessionAware{
	private static final long serialVersionUID = 1L;
	protected T model;//定义模型对象
	/*protected Map<String,Object> session;定义为protected让子类可以直接使用*/
	public Map<String,Object> session;//因为多个包下的类需要使用这个Map的session
	protected PageBean pageBean=new PageBean();
	protected DetachedCriteria detachedCriteria = null;
	
	public void setPage(int page) {
		pageBean.setCurrentPage(page);
	}
	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}
	@Override
	public T getModel() {
		return model;
	}
	//将object转为json串
	public void java2Json(Object obj,String[] excludes){
		try {
			JsonConfig jsonConfig=new JsonConfig();
			jsonConfig.setExcludes(excludes);//排除过滤掉pagebean中不需要响应到客户端的属性
			String json = JSONObject.fromObject(obj,jsonConfig).toString();
			ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
			ServletActionContext.getResponse().getWriter().println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//将list转为json数组
	public void java2JsonArrayString(List list,String[] excludes){
		try {
			JsonConfig jsonConfig=new JsonConfig();
			jsonConfig.setExcludes(excludes);//排除过滤掉pagebean中不需要响应到客户端的属性
			String json = JSONArray.fromObject(list, jsonConfig).toString();
			ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
			ServletActionContext.getResponse().getWriter().println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//通过构造方法创建模型对象
	public BaseAction(){
		ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		//获取父类的泛型数组
		Type[] actualTypeArguments = superclass.getActualTypeArguments();
		//将泛型数组第一个作为运行时类型
		Class<T> c=(Class<T>) actualTypeArguments[0];
		try {
			model=c.newInstance();//创建model实例
			detachedCriteria=DetachedCriteria.forClass(c);
			pageBean.setDetachedCriteria(detachedCriteria);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
		
	}
	public Map<String, Object> getSession() {
		return session;
	}
	
}
