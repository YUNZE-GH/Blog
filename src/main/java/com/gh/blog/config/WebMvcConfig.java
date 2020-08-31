package com.gh.blog.config;

import com.gh.blog.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * WebMvc配置
 * @author gaohan
 * @version 1.0
 * @date 2020/7/30 17:54
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns("/**") 表示拦截所有的请求，
        // addPathPatterns("/test/**") 表示拦截/test/ 下的所有路径请求，
        // addPathPatterns("/test/*") 表示拦截/test/abc，拦截/test/aaa , 不拦截 /test/abc/def
        // addPathPatterns("/test/**").excludePathPatterns("/test/login", "/test/register") 表示拦截/test/ 下的所有路径请求，但不拦截 /test/login 和 /test/register
        List<String> list = new ArrayList<>();
        list.add("/account/**");
        list.add("/test/**");
        list.add("/sendMessage/**");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(list);
    }
}
