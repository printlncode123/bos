package com.itheima.bos.utils;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

/**
 * bos��Ŀ�µĹ�����
 *
 */
public class BosUtils {
	//��ȡstruts2�����ڴ洢��Χ���session����
	public static Map<String,Object> getSession(){
		return ServletActionContext.getContext().getSession();
	}
	//��ȡhttpSession����
	public static HttpSession getHttpSession(){
		return ServletActionContext.getRequest().getSession();
	}
	//��ȡstruts2��session���е�����ֵ
	public static Object getValue1(String key){
		return getSession().get(key);
	}
	//��ȡHttpSession���е�����ֵ
	public static Object getValue2(String key){
			return getSession().get(key);
		}
}
