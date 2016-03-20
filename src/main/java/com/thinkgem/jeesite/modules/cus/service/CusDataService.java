/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cus.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cus.entity.CusData;
import com.thinkgem.jeesite.modules.cus.dao.CusDataDao;

/**
 * 自定义单表生成Service
 * @author zhengzy
 * @version 2016-03-20
 */
@Service
@Transactional(readOnly = true)
public class CusDataService extends CrudService<CusDataDao, CusData> {

	public CusData get(String id) {
		return super.get(id);
	}
	
	public List<CusData> findList(CusData cusData) {
		return super.findList(cusData);
	}
	
	public Page<CusData> findPage(Page<CusData> page, CusData cusData) {
		return super.findPage(page, cusData);
	}
	
	@Transactional(readOnly = false)
	public void save(CusData cusData) {
		super.save(cusData);
	}
	
	@Transactional(readOnly = false)
	public void delete(CusData cusData) {
		super.delete(cusData);
	}
	
}