package com.wteam.ug.controller;

import com.wteam.ug.entity.Address;
import com.wteam.ug.service.AddressService;
import com.wteam.ug.wrap.JsonResult;
import com.wteam.ug.wrap.ResultFactory;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
@Api(value = "地址相关接口")
public class AddressController {

    @Autowired
    AddressService addressService;

    @PostMapping("/add")
    @ApiOperation(value = "添加地址")
    public JsonResult add(@RequestBody Address address) {
        System.out.println("客户id:"+address.getCustomerId());
        boolean b = addressService.add(address);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新地址")
    public JsonResult update(@RequestBody Address address) {
        boolean b = addressService.update(address);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "永久注销地址")
    public JsonResult delete(@RequestParam long id) {
        boolean b = addressService.delete(id);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @GetMapping("/findById")
    @ApiOperation(value = "根据id查找地址")
    public JsonResult findById(@RequestParam long id) {
        Address address = addressService.findById(id);
        return ResultFactory.ok(address);
    }

    @GetMapping("/findAll")
    @ApiOperation(value = "查找所有地址")
    public JsonResult findAll() {
        List<Address> addressList = addressService.findAll();
        return ResultFactory.ok(addressList);
    }

    @GetMapping("/findByCustomer")
    @ApiOperation(value = "根据客户id查找地址")
    public JsonResult findByCustomer(@RequestParam long customerId){
        List<Address> addressList = addressService.findByCustomer(customerId);
        return ResultFactory.ok(addressList);
    }
}


