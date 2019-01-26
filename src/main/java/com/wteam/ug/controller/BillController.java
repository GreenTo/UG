package com.wteam.ug.controller;

import com.wteam.ug.entity.Bill;
import com.wteam.ug.entity.Certificate;
import com.wteam.ug.service.BillService;
import com.wteam.ug.service.CertificateService;
import com.wteam.ug.wrap.JsonResult;
import com.wteam.ug.wrap.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/bill")
@RestController
@Api(value = "财簿相关接口")
public class BillController {
	@Autowired
	BillService billService;
	@Autowired
	CertificateService certificateService;
	
	@PostMapping("/add")
	@ApiOperation(value = "添加财簿")
	public JsonResult add(@RequestBody Bill bill) {
		boolean b = billService.add(bill);
		if(b) {
			return ResultFactory.ok();
		}else {
			return ResultFactory.erro();
		}
	}
	
	@PostMapping("/update")
	@ApiOperation(value = "更新财簿")
	public JsonResult update(@RequestBody Bill bill) {
		boolean b = billService.update(bill);
		if(b) {
			return ResultFactory.ok();
		}else {
			return ResultFactory.erro();
		}
	}
	
	@GetMapping("/findById")
	@ApiOperation(value = "根据id查找财簿")
    public JsonResult findById(@RequestParam long id){
        Bill bill = billService.findById(id);
        return ResultFactory.ok(bill);
    }
	
	@PostMapping("/delete")
	@ApiOperation(value = "永久删除财簿")
	public JsonResult delete(@RequestParam long id){
	    boolean b=billService.delete(id);
	    if(b) {	    	
	    	return ResultFactory.ok();
	    }else
	    	return ResultFactory.erro();
	}
	
	@GetMapping("/findByUserName")
	@ApiOperation(value = "根据登录名查找财簿/总模糊查询")
	public JsonResult findByUserName(@RequestParam String param,
								 @RequestParam(defaultValue = "1") Integer page,
								 @RequestParam(defaultValue = "10") Integer size) {
		 if (param != null && !param.trim().equals("")) {
	            Page<Bill> bills = billService.searchByUsername(param, page, size);
	            return ResultFactory.ok(bills);
	        }else {
	            return ResultFactory.bad();
	        }
	}
	
	@GetMapping("/expenseAccount")
	@ApiOperation(value = "按月份查询明细账")
	public JsonResult expenseAccountByDate(@RequestParam Date date,@RequestParam String username) {
		if(date == null) {
			date = new Date();
		}
		List<Object> set = new ArrayList<>();
		List<Certificate> certificates = certificateService.findByDateAndUsername(date, username);
		List<List<String>> subjectList = billService.findByCurrentAndFixed(username);
		
		set.add(certificates);
		set.add(subjectList);
		return ResultFactory.ok(set);
	}
	
	@GetMapping("/findBySubjectName")
	@ApiOperation(value = "按科目名查询明细账")
	public JsonResult expenseAccountBySubject(@RequestParam String subjectName,@RequestParam String username) {
		if (subjectName != null && !subjectName.trim().equals("")) {
			List<Object> set = new ArrayList<>();
			List<Certificate> certificates = certificateService.searchBySubjectNameAndUsername(subjectName, username);
			List<List<String>> subjectList = billService.findByCurrentAndFixed(username);
			set.add(certificates);
			set.add(subjectList);
			return ResultFactory.ok(set);
		}else {
            return ResultFactory.bad();
        }
	}
	
	@PostMapping("/downLoad")
	@ApiOperation(value = "导出明细账")
	public JsonResult downLoad(@RequestBody List<Certificate> certificates , @RequestParam String path) {
		boolean b = billService.downLoad(certificates, path);
		if(b) {
			return ResultFactory.ok();
		}else {
			return ResultFactory.bad();
		}
	}
}
