package com.itheima.bos.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Workordermanage;
import com.itheima.bos.service.IWorkordermanageService;
import com.itheima.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class WorkordermanageAction extends BaseAction<Workordermanage>{
	@Autowired
	private IWorkordermanageService  WorkordermanageService;
	public String add(){
		WorkordermanageService.save(model);
		return "list";
	}
}
