package com.itheima.bos.dao;

import java.util.List;

import com.itheima.bos.dao.base.IBaseDAO;
import com.itheima.bos.domain.Region;

public interface IRegionDAO extends IBaseDAO<Region>{

	public void saveorupdate(Region regions);

	public List<Region> findListByQ(String q);

}
