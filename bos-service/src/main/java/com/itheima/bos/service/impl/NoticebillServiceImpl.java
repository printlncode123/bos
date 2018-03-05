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
		User user = (User) BosUtils.getValue1("loginUser");//��ȡ��ǰ�û�
		model.setUser(user);
		noticebillDAOImpl.save(model);
		//��ȡȡ��Ա�����õ�noticebill������
		//1.����webservice--crm�����ṩ�ķ���ͨ���ͻ���ַ��ѯ����������ע��crm�������
		//2.���ݶ���id��ȡȡ��Ա
		String decidedzoneId = proxy.findDecidedzoneByAddress(model.getPickaddress());
		if (decidedzoneId!=null) {
			Decidedzone decidedzone = decidedzoneDAOImpl.findById(decidedzoneId);
			Staff staff = decidedzone.getStaff();
			model.setStaff(staff);
			model.setOrdertype(Noticebill.ORDERTYPE_AUTO);
			
			//�������ֵ����ͣ��Զ��ֵ����˹��ֵ���
			Workbill workbill=new Workbill();
			workbill.setAttachbilltimes(0);//׷������
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//��������ʱ��
			workbill.setNoticebill(model);
			workbill.setPickstate(Workbill.PICKSTATE_NO);//����֪ͨ��������ֵ����ɹ�����ʱĬ����δȡ��״̬
			workbill.setRemark(model.getRemark());
			workbill.setStaff(staff);
			workbill.setType(Workbill.TYPE_1);//�µ�
			workbillDAOImpl.save(workbill);
		}else{
			model.setOrdertype(Noticebill.ORDERTYPE_MAN);
		}
		}
		
	}


