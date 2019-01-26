package com.wteam.ug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wteam.ug.entity.Withdraw;
import com.wteam.ug.service.CompanyService;
import com.wteam.ug.service.WithdrawService;
import com.wteam.ug.wrap.JsonResult;
import com.wteam.ug.wrap.ResultFactory;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/withdraw")
@RestController
@Api(value = "提现相关接口")
public class WithdrawController {
	@Autowired
	WithdrawService withdrawService;
	@Autowired
	CompanyService companyService;
	
	@PostMapping("/add")
	@ApiOperation(value = "添加提现单")
	public JsonResult add(@RequestParam Double amount,@RequestParam long id) {
		Withdraw withdraw = new Withdraw();
		withdraw.setAmount(amount);
		withdraw.setType("审核中");
		withdraw.setUsername(companyService.findById(id).getUsername());
		withdraw.setCompany(companyService.findById(id).getName());
		boolean b = withdrawService.add(withdraw);
		if(b) {
			return ResultFactory.ok();
		}else {
			return ResultFactory.erro();
		}
	}
	
	@PostMapping("/updateType1")
	@ApiOperation(value = "更改提现状态为'已放款'")
	public JsonResult updateType1(@RequestParam long id) {
		Withdraw withdraw = withdrawService.findById(id);
		withdraw.setType("已放款");
		boolean b = withdrawService.update(withdraw);
		if(b) {
			return ResultFactory.ok();
		}else {
			return ResultFactory.erro();
		}
	}
	
	@PostMapping("/updateType2")
	@ApiOperation(value = "更改提现状态为'已拒绝'")
	public JsonResult updateType2(@RequestParam long id) {
		Withdraw withdraw = withdrawService.findById(id);
		withdraw.setType("已拒绝");
		boolean b = withdrawService.update(withdraw);
		if(b) {
			return ResultFactory.ok();
		}else {
			return ResultFactory.erro();
		}
	}
	
	@PostMapping("/findById")
	@ApiOperation(value = "根据id查找提现单")
    public JsonResult findById(@RequestParam long id){
        Withdraw withdraw = withdrawService.findById(id);
        return ResultFactory.ok(withdraw);
    }
	
	@PostMapping("/delete")
	@ApiOperation(value = "永久删除提现单")
	public JsonResult delete(@RequestParam long id){
	    boolean b=withdrawService.delete(id);
	    if(b) {	    	
	    	return ResultFactory.ok();
	    }else
	    	return ResultFactory.erro();
	}
	
	@GetMapping("/findByPage")
	@ApiOperation(value = "提现单分页查询")
    public JsonResult findByPage(@RequestParam(defaultValue = "1") Integer page,
    		                      @RequestParam(defaultValue = "10") Integer size){
        Page<Withdraw> withdrawPage = withdrawService.findByPage(page, size);
        return ResultFactory.ok(withdrawPage);
    }
	
	@GetMapping("/findByid")
	@ApiOperation(value = "个人提现单分页查询")
    public JsonResult findById(@RequestParam long id,
    							@RequestParam(defaultValue = "1") Integer page,
    		                     @RequestParam(defaultValue = "10") Integer size){
        Page<Withdraw> withdrawPage = withdrawService.findById(id, page, size);
        return ResultFactory.ok(withdrawPage);
    }
}
