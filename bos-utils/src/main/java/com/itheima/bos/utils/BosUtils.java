package com.itheima.bos.utils;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

/**
 * bos项目下的工具类
 *
 */
public class BosUtils {
	//获取struts2中用于存储范围域的session对象
	public static Map<String,Object> getSession(){
		return ServletActionContext.getContext().getSession();
	}
	//获取httpSession对象
	public static HttpSession getHttpSession(){
		return ServletActionContext.getRequest().getSession();
	}
	//获取struts2中session域中的属性值
	public static Object getValue1(String key){
		return getSession().get(key);
	}
	//获取HttpSession域中的属性值
	public static Object getValue2(String key){
			return getSession().get(key);
		}
}
