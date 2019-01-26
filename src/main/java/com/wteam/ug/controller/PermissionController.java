package com.wteam.ug.controller;

import com.wteam.ug.entity.Permission;
import com.wteam.ug.service.PermissionService;
import com.wteam.ug.wrap.JsonResult;
import com.wteam.ug.wrap.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/permission")
@RestController
@Api(value = "权限相关接口")
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @PostMapping("/add")
    @ApiOperation(value = "添加权限")
    public JsonResult add(@RequestBody Permission permission){
        boolean b = permissionService.add(permission);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新权限信息")
    public JsonResult update(@RequestBody Permission permission){
        boolean b = permissionService.update(permission);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "永久删除权限")
    public JsonResult delete(@RequestParam long id){
        boolean b = permissionService.delete(id);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @GetMapping("/findById")
    @ApiOperation(value = "根据id查找权限")
    public JsonResult findById(@RequestParam long id){
        Permission permission = permissionService.findById(id);
        return ResultFactory.ok(permission);
    }

    @GetMapping("/findAll")
    @ApiOperation(value = "查找所有权限(没有分页)")
    public JsonResult findAll(){
        List<Permission> permissionList = permissionService.findAll();
        return ResultFactory.ok(permissionList);
    }

    @GetMapping("/findByPage")
    @ApiOperation(value = "权限分页查询")
    public JsonResult findByPage(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size){
        Page<Permission> permissionPage = permissionService.findByPage(page, size);
        return ResultFactory.ok(permissionPage);
    }

    @GetMapping("/search")
    @ApiOperation(value = "模糊查询")
    public JsonResult search(@RequestParam String param,
                             @RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer size){
        if (param != null && !param.trim().equals("")) {
            Page<Permission> permissionPage = permissionService.search(param, page, size);
            return ResultFactory.ok(permissionPage);
        }else {
            return ResultFactory.bad();
        }
    }

//    @GetMapping("/findByName")
//    @ApiOperation(value = "根据行为名搜索")
//    public JsonResult findByName(@RequestParam String name,
//                                 @RequestParam(defaultValue = "1") Integer page,
//                                 @RequestParam(defaultValue = "10") Integer size){
//        if (name != null && !name.trim().equals("")){
//            Page<Permission> permissionPage = permissionService.findByName(name, page, size);
//            return ResultFactory.ok(permissionPage);
//        }else {
//            return ResultFactory.bad();
//        }
//    }
}
