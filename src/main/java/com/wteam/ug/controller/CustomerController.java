package com.wteam.ug.controller;

import com.nimbusds.jose.JOSEException;
import com.wteam.ug.entity.Customer;
import com.wteam.ug.service.CustomerService;
import com.wteam.ug.service.StatisticsService;
import com.wteam.ug.util.TokenUtils2;
import com.wteam.ug.wrap.JsonResult;
import com.wteam.ug.wrap.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/customer")
@Api(value = "/客户相关接口")
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    StatisticsService statisticsService;

    @PostMapping("/register")
	@ApiOperation(value = "客户注册")
    public JsonResult register(@RequestParam String username,@RequestParam String password,@RequestParam String phone,HttpServletRequest request){
		String token = request.getHeader("Vcode");
		String vcode = "";
		if (token != null) {
			Map<String, Object> valid = null;
			try {
				valid = TokenUtils2.valid(token);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (JOSEException e) {
				e.printStackTrace();
			}
			int i = (int) valid.get("Result");
			if (i == 0) {
				System.out.println("token解析成功");
				JSONObject jsonObject = (JSONObject) valid.get("data");
				vcode = (String) jsonObject.get("vcode");
				System.out.println("验证码:" + vcode);
				System.out.println("exp是" + jsonObject.get("exp"));
			} else if (i == 2) {
				System.out.println("token已经过期");
				return ResultFactory.erro();
			}
			Customer customer = new Customer();
			customer.setUsername(username);
			customer.setPassword(password);
			customer.setPhone(phone);
			customer.setVcode(vcode);
			System.out.println("验证码:" + vcode);
			boolean b = customerService.register(customer);
			if (b) {
                try {
                    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                    statisticsService.updateRegisteredUsers(date.parse(date.format(new Date())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
				return ResultFactory.ok();
			} else
				return ResultFactory.erro("用户名已存在!");
		}else {
			return ResultFactory.erro();
		}
    }

    @GetMapping("/verificate")
	@ApiOperation(value = "获取短信验证")
	public JsonResult verficate(@RequestParam String phone, HttpServletResponse response){
		String verficate = customerService.verficate(phone);
		if (verficate != null) {
			Map<String,Object> map = new HashMap<>();
//          建立载荷
			map.put("vcode",verficate);
//          过期时间
			map.put("exp",new Date().getTime() + 1000*60*3);//一个小时
			String Vcode = null;
			try {
				Vcode = TokenUtils2.creatToken(map);
			} catch (JOSEException e) {
				e.printStackTrace();
			}
			response.setHeader("Vcode", Vcode);
			return ResultFactory.ok(Vcode);
		}
		else {
			return ResultFactory.erro("手机号码不正确!");
		}
	}

	@PostMapping("/login")
	@ApiOperation(value = "客户登录")
	public JsonResult login(@RequestParam String username , @RequestParam String password) {
		Customer customer = customerService.login(username, password);
		if (customer == null) {
            try {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                statisticsService.updateActiveUsers(date.parse(date.format(new Date())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return ResultFactory.erro("用户名或密码错误!");
        }
		else {
			return ResultFactory.ok(customer);
		}
	}

    @ApiOperation(value = "根据id查找客户")
  	@GetMapping("/findById")
    public JsonResult findById(@RequestParam long id){
        Customer customer = customerService.preUpdate(id);
        return ResultFactory.ok(customer);
    }

    @ApiOperation(value = "更新客户基本信息")
  	@PostMapping("/update")
    public JsonResult update(@RequestBody Customer customer){
        boolean b = customerService.update(customer);
        if(b) {
        	return ResultFactory.ok();
        }else
        	return ResultFactory.erro();
    }

    @ApiOperation(value = "改客户密码")
  	@PostMapping("/editPassword")
  	public JsonResult editPassword(@RequestParam long id,
  									@RequestParam String oldPassword,
  									@RequestParam String newPassword) {
  		boolean b = customerService.changePassword(id, oldPassword,newPassword);
  		if(b) {
        	return ResultFactory.ok();
        }else
        	return ResultFactory.erro();
  	}
  	
    @ApiOperation(value = "永久注销用户")
  	@PostMapping("/delete")
	public JsonResult delete(@RequestParam long id){
	    boolean b=customerService.delete(id);
	    if(b) {
	    	return ResultFactory.ok();
	    }else
	    	return ResultFactory.erro();
	}

	@ApiOperation(value = "分页查询")
  	@GetMapping("/findByPage")
  	public JsonResult findByPage(@RequestParam(defaultValue = "1") Integer page,
  								  @RequestParam(defaultValue = "10") Integer size) {
  		Page<Customer> customerPage = customerService.findByPage(page, size);
  		return ResultFactory.ok(customerPage);
  	}
  	
}
