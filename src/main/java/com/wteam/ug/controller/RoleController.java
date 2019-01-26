package com.wteam.ug.controller;

import com.wteam.ug.entity.Role;
import com.wteam.ug.service.RoleService;
import com.wteam.ug.wrap.JsonResult;
import com.wteam.ug.wrap.ResultFactory;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@Api(value = "角色相关接口")
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping("/add")
    @ApiOperation(value = "添加角色")
    public JsonResult add(@RequestBody Role role){
        boolean b = roleService.add(role);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新角色信息")
    public JsonResult update(@RequestBody Role role){
        boolean b = roleService.update(role);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "永久删除角色")
    public JsonResult delete(@RequestParam long id){
        boolean b = roleService.delete(id);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @GetMapping("/findById")
    @ApiOperation(value = "根据id查询角色")
    public JsonResult findById(@RequestParam long id){
        Role role = roleService.findById(id);
        return ResultFactory.ok(role);
    }

    @GetMapping("/findByPage")
    @ApiOperation(value = "角色分页查询")
    public JsonResult findByPage(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size){
        Page<Role> rolePage = roleService.findByPage(page, size);
        return ResultFactory.ok(rolePage);
    }

    @GetMapping("/findAll")
    @ApiOperation(value = "查找所有角色(没有分页)")
    public JsonResult findAll(){
        List<Role> roleList = roleService.findAll();
        return ResultFactory.ok(roleList);
    }

    @GetMapping("/search")
    @ApiOperation(value = "模糊查询")
    public JsonResult search(@RequestParam String param,
                             @RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer size){
        if (param != null && !param.trim().equals("")) {
            Page<Role> rolePage = roleService.search(param, page, size);
            return ResultFactory.ok(rolePage);
        }else {
            return ResultFactory.bad();
        }
    }
}
