package com.aluji.config;

import com.aluji.component.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


//@Configuration
//public class MyMvcConfig extends WebMvcConfigurationSupport {
//
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("login");
//        registry.addViewController("/index.html").setViewName("login");
//
//    }
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//
//        registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
//    }
//}
//
@Configuration
public class MyMvcConfig implements WebMvcConfigurer{

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
         registry.addViewController("/").setViewName("login");
        registry.addViewController("/index.html").setViewName("login");
        //registry.addViewController("/main.html").setViewName("list2");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/main.html")
//                .excludePathPatterns("/index.html","/","/user/login","/asserts/**","/streetlist.html","/storelist.html","/toregister","/register.html");
    }
        @Override
    public void addResourceHandlers (ResourceHandlerRegistry registry) {
       // System.out.println("add");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        //registry.addResourceHandler("/**").addResourceLocations("classpath:/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
    }
}