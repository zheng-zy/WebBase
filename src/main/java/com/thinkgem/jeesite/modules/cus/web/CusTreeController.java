/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cus.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.cus.entity.CusTree;
import com.thinkgem.jeesite.modules.cus.service.CusTreeService;

/**
 * 自定义树结构生成Controller
 * @author zhengzy
 * @version 2016-03-20
 */
@Controller
@RequestMapping(value = "${adminPath}/cus/cusTree")
public class CusTreeController extends BaseController {

	@Autowired
	private CusTreeService cusTreeService;
	
	@ModelAttribute
	public CusTree get(@RequestParam(required=false) String id) {
		CusTree entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cusTreeService.get(id);
		}
		if (entity == null){
			entity = new CusTree();
		}
		return entity;
	}
	
	@RequiresPermissions("cus:cusTree:view")
	@RequestMapping(value = {"list", ""})
	public String list(CusTree cusTree, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<CusTree> list = cusTreeService.findList(cusTree); 
		model.addAttribute("list", list);
		return "modules/cus/cusTreeList";
	}

	@RequiresPermissions("cus:cusTree:view")
	@RequestMapping(value = "form")
	public String form(CusTree cusTree, Model model) {
		if (cusTree.getParent()!=null && StringUtils.isNotBlank(cusTree.getParent().getId())){
			cusTree.setParent(cusTreeService.get(cusTree.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(cusTree.getId())){
				CusTree cusTreeChild = new CusTree();
				cusTreeChild.setParent(new CusTree(cusTree.getParent().getId()));
				List<CusTree> list = cusTreeService.findList(cusTree); 
				if (list.size() > 0){
					cusTree.setSort(list.get(list.size()-1).getSort());
					if (cusTree.getSort() != null){
						cusTree.setSort(cusTree.getSort() + 30);
					}
				}
			}
		}
		if (cusTree.getSort() == null){
			cusTree.setSort(30);
		}
		model.addAttribute("cusTree", cusTree);
		return "modules/cus/cusTreeForm";
	}

	@RequiresPermissions("cus:cusTree:edit")
	@RequestMapping(value = "save")
	public String save(CusTree cusTree, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cusTree)){
			return form(cusTree, model);
		}
		cusTreeService.save(cusTree);
		addMessage(redirectAttributes, "保存自定义树结构成功");
		return "redirect:"+Global.getAdminPath()+"/cus/cusTree/?repage";
	}
	
	@RequiresPermissions("cus:cusTree:edit")
	@RequestMapping(value = "delete")
	public String delete(CusTree cusTree, RedirectAttributes redirectAttributes) {
		cusTreeService.delete(cusTree);
		addMessage(redirectAttributes, "删除自定义树结构成功");
		return "redirect:"+Global.getAdminPath()+"/cus/cusTree/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<CusTree> list = cusTreeService.findList(new CusTree());
		for (int i=0; i<list.size(); i++){
			CusTree e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}