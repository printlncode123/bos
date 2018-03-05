package com.itheima.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.service.IDecidedzoneService;
import com.itheima.bos.web.action.base.BaseAction;
import com.itheima.crm.service.Customer;
import com.itheima.crm.service.ICustomer;

@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone>{

	//注入驱动
	@Autowired
	private IDecidedzoneService decidedzoneServiceImpl;
	
	//接收分区id参数
	private String[] subareaid;
	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}

	/**
	 *分页查询 
	 */
	public String pageQuery(){
		decidedzoneServiceImpl.pageQuery(pageBean);
		java2Json(pageBean, new String[]{"currentPage","pageSize","detachedCriteria","subareas","decidedzones"});
		return NONE;
	}
	/**
	 * 添加定区
	 */
	public String add(){
		decidedzoneServiceImpl.save(model,subareaid);
		return "decidedzoneList";
	}
	
	//注入crm客户端的代理对象（由spring创建管理）来调用crm服务获取客户信息
	@Autowired
	private ICustomer customerService;
	/**
	 * 发送ajax请求不刷新页面，查询未关联到定区的客户信息
	 * @return
	 */
	public String findNotAssociation(){
		List<Customer> cs = customerService.findNotAssociation();
		this.java2JsonArrayString(cs, new String[]{});
		return NONE;
	}
	
	/**
	 * 查询已关联到指定定区的客户信息
	 * @return
	 */
	public String findHasAssociation(){
		String id = model.getId();
		List<Customer> list = customerService.findHasAssociation(id);//crm服务的客户端的代理对象
		System.out.println(list);
		this.java2JsonArrayString(list, new String[]{});
		return NONE;
	}
	
	/**
	 * 关联客户到指定定区
	 * @return
	 */
	private List<Integer> customerIds;
	public List<Integer> getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(List<Integer> customerIds) {
		this.customerIds = customerIds;
	}

	public String assigncustomerstodecidedzone(){
		customerService.assigncustomerstodecidedzone(model.getId(), customerIds);
		return "decidedzoneList";
	}
}
