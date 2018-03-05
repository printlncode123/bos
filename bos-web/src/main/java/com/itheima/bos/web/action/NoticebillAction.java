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
	//���ݿͻ��ֻ��Ų�ѯ�ͻ���Ϣ����Ҫ����crm_bos32�ķ���������Ҫע��spring�����Ĵ�����������÷���
	@Autowired
	private ICustomer proxy;
	@Autowired
	private INoticebillService noticebillServiceImpl;
	public String findCustomerByTelephone(){
		Customer customer = proxy.findCustomerByTelephone(model.getTelephone());
		this.java2Json(customer, new String[]{});
		return NONE;
	}
	
	//�����µ��������ͻ���Ϣ���ֵ���ĳȡ��Ա��
	public String add(){
		noticebillServiceImpl.save(model);
		return "list";
	}

}
