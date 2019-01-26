package com.wteam.ug.controller;

import com.nimbusds.jose.JOSEException;
import com.wteam.ug.entity.Company;
import com.wteam.ug.service.CompanyService;
import com.wteam.ug.service.StatisticsService;
import com.wteam.ug.util.TokenUtils2;
import com.wteam.ug.wrap.JsonResult;
import com.wteam.ug.wrap.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ytx.org.apache.http.HttpResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "公司相关接口")
public class CompanyController {

    @Autowired
    CompanyService companyService;
    @Autowired
    StatisticsService statisticsService;

//    @PostMapping("/add")
//    @ApiOperation(value = "添加公司")
//    public JsonResult add(@RequestParam String username, @RequestParam String password,
//                          @RequestParam String name, @RequestParam String phone,
//                          @RequestParam String idCard, @RequestParam String type,
//                          @RequestParam String contact, @RequestParam String province,
//                          @RequestParam String city, @RequestParam String area,
//                          @RequestParam String code, @RequestParam  String detail,
//                          /*@RequestParam String businessScope,*/
//                          @RequestParam List<Long> businessScopeIdList,
//                          @RequestParam MultipartFile[] files){
//        Company company = new Company();
//        company.setUsername(username);
//        company.setPassword(password);
//        company.setName(name);
//        company.setPhone(phone);
//        company.setIdCard(idCard);
//        company.setType(type);
//        company.setContact(contact);
//        company.setProvince(province);
//        company.setCity(city);
//        company.setArea(area);
//        company.setCode(code);
//        company.setDetail(detail);
//        company.setBusinessScopeIdList(businessScopeIdList);
//        boolean b = companyService.add(company, files);
//        if (b)
//            return ResultFactory.ok();
//        else
//            return ResultFactory.erro("参数上传错误!");
//    }

    @PostMapping("/add")
    @ApiOperation(value = "添加公司")
    public JsonResult add(@RequestParam String username, @RequestParam String password,
                          @RequestParam String name, @RequestParam String phone,
                          @RequestParam String idCard, @RequestParam String type,
                          @RequestParam String contact, @RequestParam String province,
                          @RequestParam String city, @RequestParam String area,
                          @RequestParam String code, @RequestParam  String detail,
                          @RequestParam List<Long> businessScopeIdList,
                          @RequestParam(required = false) MultipartFile symbolFile,
                          @RequestParam(required = false) MultipartFile frontFile,
                          @RequestParam(required = false) MultipartFile backFile,
                          @RequestParam(required = false) MultipartFile serveFile,
                          @RequestParam(required = false) MultipartFile licenseFile){
        Company company = new Company();
        company.setUsername(username);
        company.setPassword(password);
        company.setName(name);
        company.setPhone(phone);
        company.setIdCard(idCard);
        company.setType(type);
        company.setContact(contact);
        company.setProvince(province);
        company.setCity(city);
        company.setArea(area);
        company.setCode(code);
        company.setDetail(detail);
        company.setBusinessScopeIdList(businessScopeIdList);
        boolean b = companyService.add(company, symbolFile, frontFile, backFile, serveFile, licenseFile);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @PostMapping("/login")
    @ApiOperation(value = "公司登陆后台页面")
    public JsonResult login(@RequestParam String username, @RequestParam String password, @RequestParam HttpResponse response){
        Company company = companyService.login(username, password);
        if (company == null)
            return ResultFactory.erro("用户名或密码错误!");
        else {
            Map<String,Object> map = new HashMap<>();
//            建立载荷
            map.put("id",company.getId());
            map.put("id",company.getUsername());
            map.put("symbol",1);
//            过期时间
            map.put("exp",new Date().getTime() + 1000*60*60);
            String token = null;
            try {
                token = TokenUtils2.creatToken(map);
            } catch (JOSEException e) {
                e.printStackTrace();
                return ResultFactory.erro("token创建失败!");
            }
            response.setHeader("Authorization", token);
            company.setPassword(null);
            company.setAuthorization(token);
            return ResultFactory.ok(company);
        }
    }

//    @PostMapping("/update")
//    @ApiOperation(value = "更新公司信息")
//    public JsonResult update(@RequestBody Company company){
//        boolean b = companyService.update(company);
//        if (b)
//            return ResultFactory.ok();
//        else
//            return ResultFactory.erro("参数上传错误!");
//    }

    @PostMapping("/update")
    @ApiOperation(value = "更新公司信息")
    public JsonResult update(
            @RequestParam long id,@RequestParam String name,@RequestParam String province,
                             @RequestParam String city,@RequestParam String area,
                             @RequestParam String contact,@RequestParam String phone,
                             @RequestParam String idCard,@RequestParam String code,
                             @RequestParam(required = false) MultipartFile symbolFile,
                             @RequestParam(required = false) MultipartFile frontFile,
                             @RequestParam(required = false) MultipartFile backFile,
                             @RequestParam(required = false) MultipartFile serveFile,
                             @RequestParam(required = false) MultipartFile licenseFile){
        Company company = new Company();
        company.setId(id);
        company.setName(name);
        company.setProvince(province);
        company.setCity(city);
        company.setArea(area);
        company.setContact(contact);
        company.setPhone(phone);
        company.setIdCard(idCard);
        company.setCode(code);
        boolean b = companyService.update(company, symbolFile, frontFile, backFile, serveFile, licenseFile);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @PostMapping("/editPassword")
    @ApiOperation(value = "修改公司登陆密码")
    public JsonResult editPassword(@RequestParam long id,@RequestParam String oldPassword,@RequestParam String newPassword){
        boolean b = companyService.editPassword(id, oldPassword, newPassword);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "永久注销公司")
    public JsonResult delete(@RequestParam long id){
        boolean b = companyService.delete(id);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @PostMapping("/changeStatus")
    @ApiOperation(value = "改变公司状态")
    public JsonResult changeStatus(@RequestParam long id){
        boolean b = companyService.changeStatus(id);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @GetMapping("/findById")
    @ApiOperation(value = "根据id查找公司")
    public JsonResult findById(@RequestParam long id){
        Company company = companyService.findById(id);
        try {
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            statisticsService.updateBusinessVisits(date.parse(date.format(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResultFactory.ok(company);
    }

    @GetMapping("/findByName")
    @ApiOperation(value = "根据公司名查找公司")
    public JsonResult findByName(@RequestParam String name,
                                 @RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size){
        if (name != null && name.trim().equals("")){
            Page<Company> companyPage = companyService.findByName(name, page, size);
            return ResultFactory.ok(companyPage);
        }else {
            return ResultFactory.bad();
        }
    }

//    @GetMapping("/findByType")
//    @ApiOperation(value = "根据类型查找公司")
//    public JsonResult findByType(@RequestParam String type,
//                                 @RequestParam(defaultValue = "1") Integer page,
//                                 @RequestParam(defaultValue = "10") Integer size){
//        if (type != null && type.trim().equals("")){
//            Page<Company> companyPage = companyService.findByType(type, page, size);
//            return ResultFactory.ok(companyPage);
//        }else {
//            return ResultFactory.bad();
//        }
//    }

    @GetMapping("/findByBusinessScope")
    @ApiOperation(value = "根据经营范围查找公司")
    public JsonResult findByBusinessScope(@RequestParam String businessScope,
                                 @RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size){
        if (businessScope != null && businessScope.trim().equals("")){
            Page<Company> companyPage = companyService.findByBusinessScope(businessScope, page, size);
            return ResultFactory.ok(companyPage);
        }else {
            return ResultFactory.bad();
        }
    }

//    @GetMapping("/findByStatus")
//    @ApiOperation(value = "根据公司状态查找公司")
//    public JsonResult findByStatus(@RequestParam String status,
//                                   @RequestParam(defaultValue = "1") Integer page,
//                                   @RequestParam(defaultValue = "10") Integer size){
//        if (status != null && status.trim().equals("")){
//            Page<Company> companyPage = companyService.findByStatus(status, page, size);
//            return ResultFactory.ok(companyPage);
//        }else {
//            return ResultFactory.bad();
//        }
//    }

    @GetMapping("/search")
    @ApiOperation(value = "模糊查询")
    public JsonResult search(@RequestParam String param,
                             @RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer size){
        if (param != null && param.trim().equals("")){
            Page<Company> companyPage = companyService.search(param, page, size);
            return ResultFactory.ok(companyPage);
        }else {
            return ResultFactory.bad();
        }
    }

    @GetMapping("/findByTypeAndStatus")
    @ApiOperation(value = "根据类型和状态查找公司")
    public JsonResult findByTypeAndStatus(@RequestParam String type,@RequestParam String status,
                                          @RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size){
        Page<Company> companyPage = companyService.findByTypeAndStatus(type, status,page,size);
        return ResultFactory.ok(companyPage);
    }

//    @GetMapping("/findByLngAndLat")
//    @ApiOperation(value = "根据经纬度查询周边服务(根据经营类目筛选(可传可不传))")
//    public JsonResult findByLngAndLat(@RequestParam double lng,@RequestParam double lat,@RequestParam(defaultValue = "") String bussinessScope){
//        List<Company> companyList = companyService.findByLngAndLat(lng, lat,bussinessScope);
//        return ResultFactory.ok(companyList);
//    }

//    @GetMapping("/findByLngAndLat")
//    @ApiOperation(value = "根据经纬度查询周边服务(根据经营类目筛选(可传可不传))")
//    public JsonResult findByLngAndLat(@RequestParam double lng,@RequestParam double lat,@RequestParam(defaultValue = "") String bussinessScope){
//        List<Company> companyList = companyService.findByLngAndLat(lng, lat,bussinessScope);
//        return ResultFactory.ok(companyList);
//    }


    @GetMapping("/findAroundCompanyByBusinessScope")
    @ApiOperation(value = "根据经纬度查询周边服务(根据经营类目Id筛选)(可传可不传)")
    public JsonResult findByLngAndLatAndBusinessScopeId(@RequestParam double lng,@RequestParam double lat,@RequestParam(defaultValue = "0") long bussinessScopeId){
        List<Company> companyList = companyService.findByLngAndLat(lng, lat,bussinessScopeId);
        return ResultFactory.ok(companyList);
    }

    @GetMapping("/findAroundCompanyOrderByName")
    @ApiOperation(value = "根据经纬度查询周边服务(根据公司名排序)")
    public JsonResult findAroundCompanyByName(@RequestParam double lng,@RequestParam double lat){
        List<Company> companyList = companyService.findAroundCompanyOrderByName(lng, lat);
        return ResultFactory.ok(companyList);
    }
}














