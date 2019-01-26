package com.wteam.ug.controller;

import com.wteam.ug.entity.Statistics;
import com.wteam.ug.service.StatisticsService;
import com.wteam.ug.wrap.JsonResult;
import com.wteam.ug.wrap.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RequestMapping("/statostocs")
@RestController
@Api(value = "统计相关接口")
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @PostMapping("/getRegisteredUsers")
    @ApiOperation(value = "查询注册用户量")
    public JsonResult getRegisteredUsers(@RequestParam  Date startDate,@RequestParam Date lastDate)  {
        List<Statistics> statistics = statisticsService.findByDateBetween(startDate, lastDate);
        return ResultFactory.ok(statistics);
    }

    @PostMapping("/getActiveUsers")
    @ApiOperation(value = "查询活跃用户量")
    public JsonResult getActiveUsers(@RequestParam Date startDate,@RequestParam Date lastDate) throws ParseException{
        List<Statistics> statistics = statisticsService.findByDateBetween(startDate, lastDate);
        return ResultFactory.ok(statistics);
    }

    @PostMapping("/getWorkOrder")
    @ApiOperation(value = "查询工单量")
    public JsonResult getWorkOrder(@RequestParam Date startDate,@RequestParam Date lastDate) throws ParseException {
        List<Statistics> statistics = statisticsService.findByDateBetween(startDate, lastDate);
        return ResultFactory.ok(statistics);
    }

    @PostMapping("/getBusinessVisits")
    @ApiOperation(value = "查询商户访问量")
    public JsonResult getBusinessVisits(@RequestParam Date startDate,@RequestParam Date lastDate) throws ParseException {
        List<Statistics> statistics = statisticsService.findByDateBetween(startDate, lastDate);
        return ResultFactory.ok(statistics);
    }
}
