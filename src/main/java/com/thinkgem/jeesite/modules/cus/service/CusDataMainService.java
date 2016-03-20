/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.cus.entity.CusDataMain;
import com.thinkgem.jeesite.modules.cus.dao.CusDataMainDao;
import com.thinkgem.jeesite.modules.cus.entity.CusDataChild;
import com.thinkgem.jeesite.modules.cus.dao.CusDataChildDao;

/**
 * 自定义主子表生成Service
 * @author zhengzy
 * @version 2016-03-20
 */
@Service
@Transactional(readOnly = true)
public class CusDataMainService extends CrudService<CusDataMainDao, CusDataMain> {

	@Autowired
	private CusDataChildDao cusDataChildDao;
	
	public CusDataMain get(String id) {
		CusDataMain cusDataMain = super.get(id);
		cusDataMain.setCusDataChildList(cusDataChildDao.findList(new CusDataChild(cusDataMain)));
		return cusDataMain;
	}
	
	public List<CusDataMain> findList(CusDataMain cusDataMain) {
		return super.findList(cusDataMain);
	}
	
	public Page<CusDataMain> findPage(Page<CusDataMain> page, CusDataMain cusDataMain) {
		return super.findPage(page, cusDataMain);
	}
	
	@Transactional(readOnly = false)
	public void save(CusDataMain cusDataMain) {
		super.save(cusDataMain);
		for (CusDataChild cusDataChild : cusDataMain.getCusDataChildList()){
			if (cusDataChild.getId() == null){
				continue;
			}
			if (CusDataChild.DEL_FLAG_NORMAL.equals(cusDataChild.getDelFlag())){
				if (StringUtils.isBlank(cusDataChild.getId())){
					cusDataChild.setCusDataMainId(cusDataMain);
					cusDataChild.preInsert();
					cusDataChildDao.insert(cusDataChild);
				}else{
					cusDataChild.preUpdate();
					cusDataChildDao.update(cusDataChild);
				}
			}else{
				cusDataChildDao.delete(cusDataChild);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(CusDataMain cusDataMain) {
		super.delete(cusDataMain);
		cusDataChildDao.delete(new CusDataChild(cusDataMain));
	}
	
}