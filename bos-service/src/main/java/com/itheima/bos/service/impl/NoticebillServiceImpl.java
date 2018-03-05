package com.itheima.bos.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IDecidedzoneDAO;
import com.itheima.bos.dao.INoticebillDAO;
import com.itheima.bos.dao.IWorkbillDAO;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.domain.User;
import com.itheima.bos.domain.Workbill;
import com.itheima.bos.service.INoticebillService;
import com.itheima.bos.utils.BosUtils;
import com.itheima.crm.service.ICustomer;
@Service
@Transactional
public class NoticebillServiceImpl implements INoticebillService{
	@Autowired
	private INoticebillDAO noticebillDAOImpl;
	@Autowired
	private ICustomer proxy;
	@Autowired
	private IDecidedzoneDAO decidedzoneDAOImpl;
	@Autowired
	private IWorkbillDAO workbillDAOImpl;
	@Override
	public void save(Noticebill model) {
		User user = (User) BosUtils.getValue1("loginUser");//获取当前用户
		model.setUser(user);
		noticebillDAOImpl.save(model);
		//获取取派员并设置到noticebill对象中
		//1.根据webservice--crm服务提供的服务（通过客户地址查询定区），需注入crm代理对象
		//2.根据定区id获取取派员
		String decidedzoneId = proxy.findDecidedzoneByAddress(model.getPickaddress());
		if (decidedzoneId!=null) {
			Decidedzone decidedzone = decidedzoneDAOImpl.findById(decidedzoneId);
			Staff staff = decidedzone.getStaff();
			model.setStaff(staff);
			model.setOrdertype(Noticebill.ORDERTYPE_AUTO);
			
			//工单，分单类型（自动分单，人工分单）
			Workbill workbill=new Workbill();
			workbill.setAttachbilltimes(0);//追单次数
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//工单创建时间
			workbill.setNoticebill(model);
			workbill.setPickstate(Workbill.PICKSTATE_NO);//生成通知单后立马分单生成工单此时默认是未取件状态
			workbill.setRemark(model.getRemark());
			workbill.setStaff(staff);
			workbill.setType(Workbill.TYPE_1);//新单
			workbillDAOImpl.save(workbill);
		}else{
			model.setOrdertype(Noticebill.ORDERTYPE_MAN);
		}
		}
		
	}


