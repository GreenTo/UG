package com.wteam.ug.controller;

import com.nimbusds.jose.JOSEException;
import com.wteam.ug.entity.User;
import com.wteam.ug.service.UserService;
import com.wteam.ug.util.TokenUtils2;
import com.wteam.ug.wrap.CodeStateEnum;
import com.wteam.ug.wrap.JsonResult;
import com.wteam.ug.wrap.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@RestController
@Api(value = "用户相关接口")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public JsonResult register(@RequestParam String username,@RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        boolean b = userService.register(user);
        if (b) {
            return ResultFactory.ok();
        }
        else
            return ResultFactory.erro("用户名已存在!");
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public JsonResult login(@RequestParam String username, @RequestParam String password
            , HttpServletResponse response){
        User user = userService.login(username, password);
        if (user != null) {
            Map<String,Object> map = new HashMap<>();
//          建立载荷,
            map.put("id",user.getId());
            map.put("username",user.getUsername());
            map.put("symbol",0);
//          过期时间
            map.put("exp",new Date().getTime() + 1000*60*60);//一个小时
            String token = null;
            try {
                token = TokenUtils2.creatToken(map);
            } catch (JOSEException e) {
                e.printStackTrace();
                return ResultFactory.erro("token创建失败!");
            }
            response.setHeader("Authorization", token);
            user.setAuthorization(token);
            return ResultFactory.ok(user);
        }else {
            return ResultFactory.erro(CodeStateEnum.BAD);
        }
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新用户信息")
    public JsonResult update(@RequestBody User user){
        boolean b = userService.update(user);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.erro();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "永久删除用户")
    public JsonResult delete(@RequestParam long id){
        boolean b = userService.delete(id);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.erro();
    }

//    修改密码
    @PostMapping("/editPassword")
    @ApiOperation(value = "更改用户登录密码")
    public JsonResult editPassword(@RequestParam long id,@RequestParam String oldPassword,@RequestParam String newPassword){
        boolean b = userService.editPassword(id, oldPassword, newPassword);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.erro("密码错误!");
    }

//    查找单个
    @GetMapping("/findById")
    @ApiOperation(value = "根据id查找用户")
    public JsonResult findById(@RequestParam long id){
        User user = userService.findById(id);
        user.setPassword(null);
        return ResultFactory.ok(user);
    }

//    查询所有
    @GetMapping("/findAll")
    @ApiOperation(value = "查找所有用户(没有分页)")
    public JsonResult findAll(){
        List<User> userList = userService.findAll();
        return ResultFactory.ok(userList);
    }

//    分页查询
    @GetMapping("/findByPage")
    @ApiOperation(value = "用户分页查询")
    public JsonResult findByPage(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size){
        Page<User> userPage = userService.findByPage(page, size);
        return ResultFactory.ok(userPage);
    }

//    模糊搜索
    @GetMapping("/search")
    @ApiOperation(value = "模糊查询")
    public JsonResult search(@RequestParam String param,
                             @RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer size){
        if (param != null && !param.trim().equals("")) {
            Page<User> userPage = userService.search(param, page, size);
            return ResultFactory.ok(userPage);
        }else {
            return ResultFactory.bad();
        }
    }

}
