package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IRegionDAO;
import com.itheima.bos.domain.Region;
import com.itheima.bos.service.IRegionService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class RegionService implements IRegionService{
	@Autowired
	private IRegionDAO regionDAO;
	@Override
	public void saveBatch(List<Region> regions) {
			for (Region region : regions) {
				regionDAO.saveorupdate(region);
			}
	}
	@Override
	public void pageQuery(PageBean pageBean) {
		regionDAO.pageQuery(pageBean);
	}
	@Override
	public List<Region> findAll() {
		List<Region> regionList = regionDAO.findAll();
		return regionList;
	}
	
	@Override
	public List<Region> findByQ(String q) {
		List<Region> regions=regionDAO.findListByQ(q);
		return regions;
	}

}
