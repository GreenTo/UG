package com.wteam.ug.controller;

import com.wteam.ug.entity.Employee;
import com.wteam.ug.service.EmployeeService;
import com.wteam.ug.wrap.JsonResult;
import com.wteam.ug.wrap.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/employee")
@Api(value = "员工相关接口")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/add")
    @ApiOperation(value = "添加员工")
    public JsonResult add(@RequestParam long id, @RequestParam String username, @RequestParam String password,
                          @RequestParam String phone, @RequestParam String name,
                          @RequestParam String idCard, @RequestParam long companyId,
                          @RequestParam List<Long> roleId, @RequestParam MultipartFile[] files){
        Employee employee = new Employee();
        employee.setId(id);
        employee.setIdCard(idCard);
        employee.setUsername(username);
        employee.setName(name);
        employee.setPassword(password);
        employee.setPhone(phone);
        employee.setRoles(roleId);
        employee.setCompanyId(companyId);
        boolean b = employeeService.add(employee, files);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @PostMapping("/login")
    @ApiOperation(value = "员工登录")
    public JsonResult login(@RequestParam String username,@RequestParam String password){
        Employee employee = employeeService.login(username, password);
        if (employee == null)
            return ResultFactory.erro("用户名或密码错误!");
        else {
            return ResultFactory.ok(employee);
        }
    }

    @ApiOperation(value = "更新员工信息")
    @PostMapping("/update")
    public JsonResult update(@RequestBody Employee employee){
        boolean b = employeeService.update(employee);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @ApiOperation(value = "修改员工密码")
    @PostMapping("/editPassword")
    public JsonResult editPassword(@RequestParam long id,@RequestParam String oldPassword,@RequestParam String newPassword){
        boolean b = employeeService.editPassword(id, oldPassword, newPassword);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.erro("密码错误!");
    }

    @PostMapping("/delete")
    @ApiOperation(value = "永久注销员工")
    public JsonResult delete(@RequestParam long id){
        boolean b = employeeService.delete(id);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @ApiOperation(value = "根据id查找员工")
    @GetMapping("/findById")
    public JsonResult findById(@RequestParam long id){
        Employee employee = employeeService.findById(id);
        return ResultFactory.ok(employee);
    }

    @ApiOperation(value = "根据员工号查找员工")
    @GetMapping("/findByNumber")
    public JsonResult findByNumber(@RequestParam String number,@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer size){
        if (number != null && !number.trim().equals("")){
            Page<Employee> employeePage = employeeService.findByNumber(number, page, size);
            return ResultFactory.ok(employeePage);
        }else {
            return ResultFactory.bad();
        }
    }

    @ApiOperation(value = "根据员工姓名查找员工")
    @GetMapping("/findByName")
    public JsonResult findByName(@RequestParam String name,@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size){
        if (name != null && !name.trim().equals("")){
            Page<Employee> employeePage = employeeService.findByName(name, page, size);
            return ResultFactory.ok(employeePage);
        }else {
            return ResultFactory.bad();
        }
    }

    @ApiOperation(value = "根据电话查找员工")
    @GetMapping("/findByPhone")
    public JsonResult findByPhone(@RequestParam String phone,@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer size){
        if (phone != null && !phone.trim().equals("")){
            Page<Employee> employeePage = employeeService.findByPhone(phone, page, size);
            return ResultFactory.ok(employeePage);
        }else {
            return ResultFactory.bad();
        }
    }

    @ApiOperation(value = "模糊查询")
    @GetMapping("/search")
    public JsonResult search(@RequestParam String param,@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer size){
        if (param != null && !param.trim().equals("")){
            Page<Employee> employeePage = employeeService.search(param, page, size);
            return ResultFactory.ok(employeePage);
        }else {
            return ResultFactory.bad();
        }
    }
}
