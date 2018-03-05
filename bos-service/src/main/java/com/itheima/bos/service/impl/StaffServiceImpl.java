package com.itheima.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IStaffDAO;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.IStaffService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class StaffServiceImpl implements IStaffService{
	@Autowired
	private IStaffDAO staffDAO;
	@Override
	public void save(Staff model) {
		staffDAO.save(model);
	}
	@Override
	public void pageQuery(PageBean pageBean) {
		staffDAO.pageQuery(pageBean);
	}
	@Override
	public void deleteBatch(String ids) {
		//ids=["","",""....],将ids用,分割成数组循环遍历
		String[] split = ids.split(",");
		for (String id : split) {
		staffDAO.executeUpdate("staff.deleteBatch", id);//调用通用的更新方法，需在对应的xml中配置<query name="queryName">,id是条件
		}
	}
	@Override
	public Staff findById(String id) {
		Staff staff = staffDAO.findById(id);
		return staff;
	}
	@Override
	public void update(Staff staff) {
		staffDAO.update(staff);
	}
	
	public List<Staff> findNotDel() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);//创建staff的离线条件实例
		detachedCriteria.add(Restrictions.eq("deltag", "0"));//添加条件过滤查询
		List<Staff> staffs = staffDAO.findByDetachedCriteria(detachedCriteria);//根据条件查询
		return staffs;
	}

}
