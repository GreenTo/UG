package com.wteam.ug.controller;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wteam.ug.entity.Certificate;
import com.wteam.ug.service.CertificateService;
import com.wteam.ug.wrap.JsonResult;
import com.wteam.ug.wrap.ResultFactory;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/certificate")
@RestController
@Api(value = "凭证相关接口")
public class CertificateController {
	
	@Autowired
	CertificateService certificateService;
	
	@PostMapping("/add")
	@ApiOperation(value = "添加凭证")
	public JsonResult add(@RequestBody Certificate certificate) throws ParseException {
		boolean a = certificateService.add(certificate);
		boolean b = certificateService.addInBill(certificate.getId());
		boolean c = certificateService.addOverr(certificate.getId());
		if(a&b&c) {
			return ResultFactory.ok();
		}else {
			return ResultFactory.erro();
		}
	}
	
	@PostMapping("/update")
	@ApiOperation(value = "更新凭证")
	public JsonResult update(@RequestBody Certificate certificate) {
		boolean b = certificateService.update(certificate);
		if(b) {
			return ResultFactory.ok();
		}else {
			return ResultFactory.erro();
		}
	}
	
	@PostMapping("/findById")
	@ApiOperation(value = "根据id查找凭证")
    public JsonResult findById(@RequestParam long id){
        Certificate certificate = certificateService.findById(id);
        return ResultFactory.ok(certificate);
    }
	
	@PostMapping("/delete")
	@ApiOperation(value = "永久删除凭证")
	public JsonResult delete(@RequestParam long id){
	    boolean b=certificateService.delete(id);
	    if(b) {	    	
	    	return ResultFactory.ok();
	    }else
	    	return ResultFactory.erro();
	}
	
	@GetMapping("/findByUserName")
	@ApiOperation(value = "根据用户名查找凭证")
	public JsonResult findByUserName(@RequestParam String param,
								 @RequestParam(defaultValue = "1") Integer page,
								 @RequestParam(defaultValue = "10") Integer size) {
		 if (param != null && !param.trim().equals("")) {
	            Page<Certificate> certificates = certificateService.searchByUserName(param, page, size);
	            return ResultFactory.ok(certificates);
	        }else {
	            return ResultFactory.bad();
	        }
	}
	
	@GetMapping("/findByCertificateId")
	@ApiOperation(value = "根据凭证号查找凭证")
	public JsonResult findByCertificateId(@RequestParam String param,
								 @RequestParam(defaultValue = "1") Integer page,
								 @RequestParam(defaultValue = "10") Integer size) {
		 if (param != null && !param.trim().equals("")) {
	            Page<Certificate> certificates = certificateService.searchByCertificateId(param, page, size);
	            return ResultFactory.ok(certificates);
	        }else {
	            return ResultFactory.bad();
	        }
	}
	
	@GetMapping("/findBySummary")
	@ApiOperation(value = "根据摘要查找凭证")
	public JsonResult findBySummary(@RequestParam String param,
								 @RequestParam(defaultValue = "1") Integer page,
								 @RequestParam(defaultValue = "10") Integer size) {
		 if (param != null && !param.trim().equals("")) {
	            Page<Certificate> certificates = certificateService.searchBySummary(param, page, size);
	            return ResultFactory.ok(certificates);
	        }else {
	            return ResultFactory.bad();
	        }
	}
	
	@GetMapping("/searchAll")
	@ApiOperation(value = "总模糊查询")
	public JsonResult search(@RequestParam String param,
								 @RequestParam(defaultValue = "1") Integer page,
								 @RequestParam(defaultValue = "10") Integer size) {
		 if (param != null && !param.trim().equals("")) {
	            Page<Certificate> certificates = certificateService.searchAll(param, page, size);
	            return ResultFactory.ok(certificates);
	        }else {
	            return ResultFactory.bad();
	        }
	}
	
	@GetMapping("/findByDate")
	@ApiOperation(value = "根据时间查找凭证")
	public JsonResult findByDate(@RequestParam Date date,
								 @RequestParam(defaultValue = "1") Integer page,
								 @RequestParam(defaultValue = "10") Integer size) {
	    if(date == null) {
	    	date = new Date();
	    	Page<Certificate> certificates = certificateService.findByDate(date, page, size);
	    	return ResultFactory.ok(certificates);
	    }else {
		Page<Certificate> certificates = certificateService.findByDate(date, page, size);
		return ResultFactory.ok(certificates);
		}
	       
	}
}
