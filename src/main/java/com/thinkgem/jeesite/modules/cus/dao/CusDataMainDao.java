/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cus.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cus.entity.CusDataMain;

/**
 * 自定义主子表生成DAO接口
 * @author zhengzy
 * @version 2016-03-20
 */
@MyBatisDao
public interface CusDataMainDao extends CrudDao<CusDataMain> {
	
}