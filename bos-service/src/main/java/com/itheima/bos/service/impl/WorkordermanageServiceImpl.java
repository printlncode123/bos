package com.itheima.bos.service.impl;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IWorkordermanageDAO;
import com.itheima.bos.domain.Workordermanage;
import com.itheima.bos.service.IWorkordermanageService;
@Service
@Transactional
public class WorkordermanageServiceImpl implements IWorkordermanageService{
	@Autowired
	private IWorkordermanageDAO workordermanageDAOImpl;//由spring的ioc容器注入创建
	String flag="1";
	@Override
	public void save(Workordermanage model) {
		try{
		workordermanageDAOImpl.save(model);
		}catch (Exception e) {
			flag="0";
			ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
			try {
				ServletActionContext.getResponse().getWriter().println(flag);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
}
