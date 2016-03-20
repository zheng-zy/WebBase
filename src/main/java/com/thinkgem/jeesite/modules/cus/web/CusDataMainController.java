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
import com.thinkgem.jeesite.modules.cus.entity.CusDataMain;
import com.thinkgem.jeesite.modules.cus.service.CusDataMainService;

/**
 * 自定义主子表生成Controller
 * @author zhengzy
 * @version 2016-03-20
 */
@Controller
@RequestMapping(value = "${adminPath}/cus/cusDataMain")
public class CusDataMainController extends BaseController {

	@Autowired
	private CusDataMainService cusDataMainService;
	
	@ModelAttribute
	public CusDataMain get(@RequestParam(required=false) String id) {
		CusDataMain entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cusDataMainService.get(id);
		}
		if (entity == null){
			entity = new CusDataMain();
		}
		return entity;
	}
	
	@RequiresPermissions("cus:cusDataMain:view")
	@RequestMapping(value = {"list", ""})
	public String list(CusDataMain cusDataMain, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CusDataMain> page = cusDataMainService.findPage(new Page<CusDataMain>(request, response), cusDataMain); 
		model.addAttribute("page", page);
		return "modules/cus/cusDataMainList";
	}

	@RequiresPermissions("cus:cusDataMain:view")
	@RequestMapping(value = "form")
	public String form(CusDataMain cusDataMain, Model model) {
		model.addAttribute("cusDataMain", cusDataMain);
		return "modules/cus/cusDataMainForm";
	}

	@RequiresPermissions("cus:cusDataMain:edit")
	@RequestMapping(value = "save")
	public String save(CusDataMain cusDataMain, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cusDataMain)){
			return form(cusDataMain, model);
		}
		cusDataMainService.save(cusDataMain);
		addMessage(redirectAttributes, "保存自定义主子表成功");
		return "redirect:"+Global.getAdminPath()+"/cus/cusDataMain/?repage";
	}
	
	@RequiresPermissions("cus:cusDataMain:edit")
	@RequestMapping(value = "delete")
	public String delete(CusDataMain cusDataMain, RedirectAttributes redirectAttributes) {
		cusDataMainService.delete(cusDataMain);
		addMessage(redirectAttributes, "删除自定义主子表成功");
		return "redirect:"+Global.getAdminPath()+"/cus/cusDataMain/?repage";
	}

}