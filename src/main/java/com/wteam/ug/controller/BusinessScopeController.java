package com.wteam.ug.controller;

import com.wteam.ug.entity.BusinessScope;
import com.wteam.ug.service.BusinessScopeService;
import com.wteam.ug.wrap.JsonResult;
import com.wteam.ug.wrap.ResultFactory;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/businessScope")
@RestController
@Api(value = "经营范围管理相关接口")
public class BusinessScopeController {

    @Resource(name = "businessScope1")
    BusinessScopeService businessScopeService;

    @PostMapping("/add")
    @ApiOperation(value = "添加经营类目")
    public JsonResult add(@RequestBody BusinessScope businessScope){
        boolean b = businessScopeService.add(businessScope);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新经营类目信息")
    public JsonResult update(@RequestBody BusinessScope businessScope){
        boolean b = businessScopeService.update(businessScope);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "永久注销经营类目")
    public JsonResult delete(@RequestParam long id){
        boolean b = businessScopeService.delete(id);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @GetMapping("/findById")
    @ApiOperation(value = "根据id查找经营类目")
    public JsonResult findById(@RequestParam long id){
        BusinessScope businessScope = businessScopeService.findById(id);
        return ResultFactory.ok(businessScope);
    }

    @GetMapping("/findAll")
    @ApiOperation(value = "查找所有")
    public JsonResult findAll(){
        List<BusinessScope> scopeList = businessScopeService.findAll();
        return ResultFactory.ok(scopeList);
    }
}
