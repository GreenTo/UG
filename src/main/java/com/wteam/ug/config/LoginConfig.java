package com.wteam.ug.config;

import com.wteam.ug.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

//@Configuration
public class LoginConfig extends WebMvcConfigurerAdapter {

    @Autowired
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> paths = new ArrayList<>();
        paths.add("/user/delete");
        paths.add("/user/editPassword");
        paths.add("/role/**");
        paths.add("/permission/**");
        paths.add("/subject/**");
        paths.add("/order/assign");
        paths.add("/order/distribute");
        paths.add("/order/update");
        paths.add("/statistics/**");
        paths.add("/menu/**");
        paths.add("/menuItem/**");
        paths.add("/log/**");
        paths.add("/employee/delete");
        paths.add("/employee/update");
        paths.add("/employee/add");
        paths.add("/company/add");
        paths.add("/company/delete");
        paths.add("/company/changeStatus");
        paths.add("/company/editPassword");
        paths.add("/company/update");
        paths.add("/businessScope/add");
        paths.add("/businessScope/delete");
        paths.add("/businessScope/update");
        registry.addInterceptor(loginInterceptor).addPathPatterns(paths);
    }
}
