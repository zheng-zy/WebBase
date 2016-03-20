/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cus.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 自定义主子表生成Entity
 * @author zhengzy
 * @version 2016-03-20
 */
public class CusDataChild extends DataEntity<CusDataChild> {
	
	private static final long serialVersionUID = 1L;
	private CusDataMain cusDataMainId;		// 业务主表ID 父类
	private String name;		// 名称
	
	public CusDataChild() {
		super();
	}

	public CusDataChild(String id){
		super(id);
	}

	public CusDataChild(CusDataMain cusDataMainId){
		this.cusDataMainId = cusDataMainId;
	}

	@Length(min=0, max=64, message="业务主表ID长度必须介于 0 和 64 之间")
	public CusDataMain getCusDataMainId() {
		return cusDataMainId;
	}

	public void setCusDataMainId(CusDataMain cusDataMainId) {
		this.cusDataMainId = cusDataMainId;
	}
	
	@Length(min=0, max=100, message="名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}