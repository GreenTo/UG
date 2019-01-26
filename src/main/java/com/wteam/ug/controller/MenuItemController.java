package com.wteam.ug.controller;

import com.wteam.ug.entity.MenuItem;
import com.wteam.ug.service.MenuItemService;
import com.wteam.ug.wrap.JsonResult;
import com.wteam.ug.wrap.ResultFactory;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "菜单项相关接口")
public class MenuItemController {
	
	@Autowired
	MenuItemService menuItemService;
	
	@PostMapping("/menuItem/add")
	@ApiOperation(value = "添加菜单项")
	public JsonResult add(@RequestBody MenuItem menuItem) {
		boolean b=menuItemService.add(menuItem);
		if(b) {	
			return ResultFactory.ok();
		}else
			return ResultFactory.erro("添加失败");
	}
	
	@GetMapping("/menuItem/findById")
	@ApiOperation(value = "根据id查找菜单项")
    public JsonResult preUpdate(@RequestParam Integer id){
        MenuItem menuItem = menuItemService.preUpdate(id);
        return ResultFactory.ok(menuItem);
    }
	
	@PostMapping("/menuItem/update")
	@ApiOperation(value = "更新菜单项信息")
    public JsonResult update(@RequestBody MenuItem menuItem){
        boolean b=menuItemService.update(menuItem);
        if(b) {
        	return ResultFactory.ok();
        }else
        	return ResultFactory.erro();
    }
	
	@PostMapping("/menuItem/delete")
	@ApiOperation(value = "永久删除菜单项")
	public JsonResult delete(@RequestBody Integer id){
	    boolean b=menuItemService.delete(id);
	    if(b) {	    	
	    	return ResultFactory.ok();
	    }else
	    	return ResultFactory.erro("该菜单项存在子菜单项!");
	}
	
//	@GetMapping("/menuItem/findTotal")
//    public JsonResult findTotal(){
//        long total = menuItemService.findTotal();
//        return ResultFactory.ok(total);
//    }
	
	//查询菜单下所有菜单项
	@PostMapping("/menuItem/findAllMenuItem")
	@ApiOperation(value = "根据菜单id查询所有菜单项")
	public JsonResult findAllMenuItem(@RequestParam Integer id) {
		List<MenuItem> menuItems = menuItemService.findAllMenuItem(id);
		return ResultFactory.ok(menuItems);
	}
}
