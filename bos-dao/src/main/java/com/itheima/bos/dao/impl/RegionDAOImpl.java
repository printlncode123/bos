package com.itheima.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IRegionDAO;
import com.itheima.bos.dao.base.impl.BaseDAOImpl;
import com.itheima.bos.domain.Region;
@Repository
public class RegionDAOImpl extends BaseDAOImpl<Region> implements IRegionDAO{

	/**
	 * private String province;
	private String city;
	private String district;
	private String postcode;
	private String shortcode;
	private String citycode;
	 */
	@Override
	public List<Region> findListByQ(String q) {
		String hql="FROM Region r where r.province LIKE ? OR r.city LIKE ? OR r.district LIKE ? OR"
				+ " r.postcode LIKE ? OR r.shortcode LIKE ? OR r.citycode LIKE ?";
		List<Region> find = (List<Region>) this.getHibernateTemplate().find(hql, "%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%");
		return find;
	}
}
