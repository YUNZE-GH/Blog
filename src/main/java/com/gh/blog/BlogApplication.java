package com.gh.blog;

import com.thebeastshop.forest.springboot.annotation.ForestScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/11 21:11
 */
@SpringBootApplication
// 表示扫描dao包下的所有类，将其加入到IOC容器，也就是说dao包下的类可以不加@Repository注解
@MapperScan("com.gh.blog.dao")
@ForestScan(basePackages = "com.gh.blog.client")
public class BlogApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
        return application.sources(BlogApplication.class);
    }
}
