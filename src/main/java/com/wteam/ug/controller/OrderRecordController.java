package com.wteam.ug.controller;

import com.wteam.ug.entity.OrderRecord;
import com.wteam.ug.service.OrderRecordService;
import com.wteam.ug.wrap.JsonResult;
import com.wteam.ug.wrap.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orderRecord")
public class OrderRecordController {

    @Autowired
    OrderRecordService orderRecordService;

    @RequestMapping("/findByCustomerId")
    public JsonResult findByCustomerId(@RequestParam long id){
        List<OrderRecord> orderRecordList = orderRecordService.findCustomerId(id);
        return ResultFactory.ok(orderRecordList);
    }
}
