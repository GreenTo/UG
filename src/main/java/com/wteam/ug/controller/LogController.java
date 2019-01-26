package com.wteam.ug.controller;

import com.wteam.ug.entity.Log;
import com.wteam.ug.repository.LogRepository;
import com.wteam.ug.wrap.JsonResult;
import com.wteam.ug.wrap.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
@Api(value = "日志相关接口")
public class LogController {

    @Autowired
    LogRepository logRepository;

    @ApiOperation(value = "日志分页查询")
    @GetMapping("/findByPage")
    public JsonResult findByPage(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size){
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<Log> logPage = logRepository.findAll(pageRequest);
        return ResultFactory.ok(logPage);
    }
}
