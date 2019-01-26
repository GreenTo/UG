package com.wteam.ug.controller;

import com.wteam.ug.entity.Menu;
import com.wteam.ug.service.MenuService;
import com.wteam.ug.wrap.JsonResult;
import com.wteam.ug.wrap.ResultFactory;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "菜单相关接口")
public class MenuController {
	
	@Autowired
	MenuService menuService;
	
	//添加菜单
	@PostMapping("/menu/add")
	@ApiOperation(value = "添加菜单")
	public JsonResult add(@RequestBody Menu menu) {
		boolean b=menuService.add(menu);
		if(b) {
			return ResultFactory.ok();
		}else
			return ResultFactory.erro("添加失败");
	}
	
	//查询所有菜单
	@GetMapping("/menu/findAll")
	public JsonResult findAll() {
		List<Menu> menus=menuService.findAll();
	    return ResultFactory.ok(menus);
	}
	

	@GetMapping("/menu/findById")
	@ApiOperation(value = "根据id查找菜单")
    public JsonResult preUpdate(@RequestParam Integer id){
        Menu menu = menuService.preUpdate(id);
        return ResultFactory.ok(menu);
    }

	//更新菜单
	@PostMapping("/menu/update")
	@ApiOperation(value = "更新菜单信息")
    public JsonResult update(@RequestBody Menu menu){
        boolean b=menuService.update(menu);
        if(b) {
        	return ResultFactory.ok();
        }else
        	return ResultFactory.erro();
    }
	
	//删除菜单
	@PostMapping("/menu/delete")
	@ApiOperation(value = "永久删除菜单")
	public JsonResult delete(@RequestBody Integer id){
	    boolean b=menuService.delete(id);
	    if(b) {
	    	return ResultFactory.ok();
	    }else
	    	return ResultFactory.erro();
	}

	//菜单分页
	@GetMapping("/menu/findByPage")
	@ApiOperation(value = "菜单分页查询")
    public JsonResult findByPage(@RequestParam(defaultValue = "1") Integer page,
    		                      @RequestParam(defaultValue = "10") Integer size){
        Page<Menu> menuPage = menuService.findByPage(page, size);
//        List<Menu> menus = menuPage.getContent();
//        int number=menuPage.getNumber();
        return ResultFactory.ok(menuPage);
    }
	
	//根据菜单创建时间分页
//	@GetMapping("/menu/findByDate")
//    public JsonResult findByDate(@RequestParam(defaultValue = "1") Integer page,
//            					  @RequestParam(defaultValue = "10") Integer size){
//        Page<Menu> menuPage = menuService.findByDate(page,size);
//        List<Menu> menus = menuPage.getContent();
//        int number=menuPage.getNumber();
//        return ResultFactory.ok(menus,number);
//    }
	
//	//查询菜单总数
//	@GetMapping("/menu/findTotal")
//    public JsonResult findTotal(){
//		long total = menuService.findTotal();
//        return ResultFactory.ok(total);
//    }
	
	//根据菜单名查询菜单
	@GetMapping("/menu/findByName")
	@ApiOperation(value = "根据名字查找菜单")
	public JsonResult findByName(@RequestParam String name) {
		List<Menu> menus=menuService.findByName(name);
	    return ResultFactory.ok(menus);
	}
	
	//模糊查询并分页
	@GetMapping("/menu/search")
	@ApiOperation(value = "模糊查询")
	public JsonResult search(@RequestParam String param,
							  @RequestParam(defaultValue = "1") Integer page,
    		                  @RequestParam(defaultValue = "10") Integer size) {
		 if (param != null && !param.trim().equals("")) {
	            Page<Menu> menuPage = menuService.search(param, page, size);
	            return ResultFactory.ok(menuPage);
	        }else {
	            return ResultFactory.bad();
	        }
	}
	

}
