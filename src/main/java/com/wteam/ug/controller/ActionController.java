package com.wteam.ug.controller;

import com.wteam.ug.entity.Action;
import com.wteam.ug.service.ActionService;
import com.wteam.ug.wrap.JsonResult;
import com.wteam.ug.wrap.ResultFactory;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/action")
@RestController
@Api(value = "行为相关接口")
public class ActionController {

    @Autowired
    ActionService actionService;

    @PostMapping("/add")
    @ApiOperation(value = "添加行为")
    public JsonResult add(@RequestBody Action action){
        boolean b = actionService.add(action);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新行为信息")
    public JsonResult update(@RequestBody Action action){
        boolean b = actionService.update(action);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "永久注销行为")
    public JsonResult delete(@RequestParam long id){
        boolean b = actionService.delete(id);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @GetMapping("/findById")
    @ApiOperation(value = "根据id查找行为")
    public JsonResult findById(@RequestParam long id){
        Action action = actionService.findById(id);
        return ResultFactory.ok(action);
    }

    @GetMapping("/findAll")
    @ApiOperation(value = "查找所有行为")
    public JsonResult findAll(){
        List<Action> actionList = actionService.findAll();
        return ResultFactory.ok(actionList);
    }

    @GetMapping("/findByPage")
    @ApiOperation(value = "行为分页查询")
    public JsonResult findByPage(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size){
        Page<Action> actionPage = actionService.findByPage(page, size);
        return ResultFactory.ok(actionPage);
    }

    @GetMapping("/search")
    @ApiOperation(value = "模糊查询")
    public JsonResult search(@RequestParam String param,
                             @RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer size){
        if (param == null || param.trim().equals("")){
            return ResultFactory.bad();
        }else {
            Page<Action> actionPage = actionService.search(param, page, size);
            return ResultFactory.ok(actionPage);
        }
    }
}
