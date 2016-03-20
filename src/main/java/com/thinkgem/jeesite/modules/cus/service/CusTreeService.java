/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cus.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.cus.entity.CusTree;
import com.thinkgem.jeesite.modules.cus.dao.CusTreeDao;

/**
 * 自定义树结构生成Service
 * @author zhengzy
 * @version 2016-03-20
 */
@Service
@Transactional(readOnly = true)
public class CusTreeService extends TreeService<CusTreeDao, CusTree> {

	public CusTree get(String id) {
		return super.get(id);
	}
	
	public List<CusTree> findList(CusTree cusTree) {
		if (StringUtils.isNotBlank(cusTree.getParentIds())){
			cusTree.setParentIds(","+cusTree.getParentIds()+",");
		}
		return super.findList(cusTree);
	}
	
	@Transactional(readOnly = false)
	public void save(CusTree cusTree) {
		super.save(cusTree);
	}
	
	@Transactional(readOnly = false)
	public void delete(CusTree cusTree) {
		super.delete(cusTree);
	}
	
}