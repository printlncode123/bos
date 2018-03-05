package com.itheima.bos.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.service.INoticebillService;
import com.itheima.bos.web.action.base.BaseAction;
import com.itheima.crm.service.Customer;
import com.itheima.crm.service.ICustomer;
@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill>{
	//根据客户手机号查询客户信息，需要调用crm_bos32的服务，所以需要注入spring创建的代理对象来调用服务
	@Autowired
	private ICustomer proxy;
	@Autowired
	private INoticebillService noticebillServiceImpl;
	public String findCustomerByTelephone(){
		Customer customer = proxy.findCustomerByTelephone(model.getTelephone());
		this.java2Json(customer, new String[]{});
		return NONE;
	}
	
	//新增新单（包括客户信息，分单给某取派员）
	public String add(){
		noticebillServiceImpl.save(model);
		return "list";
	}

}
