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

	//ע������
	@Autowired
	private IDecidedzoneService decidedzoneServiceImpl;
	
	//���շ���id����
	private String[] subareaid;
	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}

	/**
	 *��ҳ��ѯ 
	 */
	public String pageQuery(){
		decidedzoneServiceImpl.pageQuery(pageBean);
		java2Json(pageBean, new String[]{"currentPage","pageSize","detachedCriteria","subareas","decidedzones"});
		return NONE;
	}
	/**
	 * ��Ӷ���
	 */
	public String add(){
		decidedzoneServiceImpl.save(model,subareaid);
		return "decidedzoneList";
	}
	
	//ע��crm�ͻ��˵Ĵ��������spring��������������crm�����ȡ�ͻ���Ϣ
	@Autowired
	private ICustomer customerService;
	/**
	 * ����ajax����ˢ��ҳ�棬��ѯδ�����������Ŀͻ���Ϣ
	 * @return
	 */
	public String findNotAssociation(){
		List<Customer> cs = customerService.findNotAssociation();
		this.java2JsonArrayString(cs, new String[]{});
		return NONE;
	}
	
	/**
	 * ��ѯ�ѹ�����ָ�������Ŀͻ���Ϣ
	 * @return
	 */
	public String findHasAssociation(){
		String id = model.getId();
		List<Customer> list = customerService.findHasAssociation(id);//crm����Ŀͻ��˵Ĵ������
		System.out.println(list);
		this.java2JsonArrayString(list, new String[]{});
		return NONE;
	}
	
	/**
	 * �����ͻ���ָ������
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
