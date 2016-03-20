/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cus.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.cus.entity.CusData;
import com.thinkgem.jeesite.modules.cus.service.CusDataService;

/**
 * 自定义单表生成Controller
 * @author zhengzy
 * @version 2016-03-20
 */
@Controller
@RequestMapping(value = "${adminPath}/cus/cusData")
public class CusDataController extends BaseController {

	@Autowired
	private CusDataService cusDataService;
	
	@ModelAttribute
	public CusData get(@RequestParam(required=false) String id) {
		CusData entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cusDataService.get(id);
		}
		if (entity == null){
			entity = new CusData();
		}
		return entity;
	}
	
	@RequiresPermissions("cus:cusData:view")
	@RequestMapping(value = {"list", ""})
	public String list(CusData cusData, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CusData> page = cusDataService.findPage(new Page<CusData>(request, response), cusData); 
		model.addAttribute("page", page);
		return "modules/cus/cusDataList";
	}

	@RequiresPermissions("cus:cusData:view")
	@RequestMapping(value = "form")
	public String form(CusData cusData, Model model) {
		model.addAttribute("cusData", cusData);
		return "modules/cus/cusDataForm";
	}

	@RequiresPermissions("cus:cusData:edit")
	@RequestMapping(value = "save")
	public String save(CusData cusData, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cusData)){
			return form(cusData, model);
		}
		cusDataService.save(cusData);
		addMessage(redirectAttributes, "保存自定义单表成功");
		return "redirect:"+Global.getAdminPath()+"/cus/cusData/?repage";
	}
	
	@RequiresPermissions("cus:cusData:edit")
	@RequestMapping(value = "delete")
	public String delete(CusData cusData, RedirectAttributes redirectAttributes) {
		cusDataService.delete(cusData);
		addMessage(redirectAttributes, "删除自定义单表成功");
		return "redirect:"+Global.getAdminPath()+"/cus/cusData/?repage";
	}

}