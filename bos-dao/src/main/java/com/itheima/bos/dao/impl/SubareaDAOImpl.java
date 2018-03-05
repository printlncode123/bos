package com.itheima.bos.dao.impl;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.itheima.bos.dao.ISubareaDAO;
import com.itheima.bos.dao.base.impl.BaseDAOImpl;
import com.itheima.bos.domain.Subarea;
@Repository
public class SubareaDAOImpl extends BaseDAOImpl<Subarea> implements ISubareaDAO {

	@Override
	public List<Object> findSubareasGroupByProvince() {
		String sql="SELECT r.province ,count(*) FROM Subarea s LEFT OUTER JOIN s.region r GROUP BY r.province";//根据区域的省查询分区数量
		return (List<Object>) this.getHibernateTemplate().find(sql,null);
	}

}
