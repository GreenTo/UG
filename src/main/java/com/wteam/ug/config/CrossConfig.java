package com.wteam.ug.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CrossConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .maxAge(3600)
                .allowedMethods("*")
                .exposedHeaders("Vcode","Authorization",
                        "access-control-allow-headers" ,
                        "access-control-allow-methods" ,
                        "access-control-allow-origin" ,
                        "access-control-max-age" ,
                        "X-Frame-Options" );
    }


}
