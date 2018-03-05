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
 * ���ֲ����ĳ�ȡ
 *
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T>,SessionAware{
	private static final long serialVersionUID = 1L;
	protected T model;//����ģ�Ͷ���
	/*protected Map<String,Object> session;����Ϊprotected���������ֱ��ʹ��*/
	public Map<String,Object> session;//��Ϊ������µ�����Ҫʹ�����Map��session
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
	//��objectתΪjson��
	public void java2Json(Object obj,String[] excludes){
		try {
			JsonConfig jsonConfig=new JsonConfig();
			jsonConfig.setExcludes(excludes);//�ų����˵�pagebean�в���Ҫ��Ӧ���ͻ��˵�����
			String json = JSONObject.fromObject(obj,jsonConfig).toString();
			ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
			ServletActionContext.getResponse().getWriter().println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//��listתΪjson����
	public void java2JsonArrayString(List list,String[] excludes){
		try {
			JsonConfig jsonConfig=new JsonConfig();
			jsonConfig.setExcludes(excludes);//�ų����˵�pagebean�в���Ҫ��Ӧ���ͻ��˵�����
			String json = JSONArray.fromObject(list, jsonConfig).toString();
			ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
			ServletActionContext.getResponse().getWriter().println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ͨ�����췽������ģ�Ͷ���
	public BaseAction(){
		ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		//��ȡ����ķ�������
		Type[] actualTypeArguments = superclass.getActualTypeArguments();
		//�����������һ����Ϊ����ʱ����
		Class<T> c=(Class<T>) actualTypeArguments[0];
		try {
			model=c.newInstance();//����modelʵ��
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
