package com.gh.blog.controller;

import com.gh.blog.client.HttpForestClient;
import com.gh.blog.entity.AccountInfo;
import com.gh.blog.entity.Demo;
import com.gh.blog.entity.Loginmonitor;
import com.gh.blog.service.DemoService;
import com.gh.blog.service.LoginmonitorService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/11 21:10
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TestController {

    private Log log = LogFactory.getLog(this.getClass().getName());

    @Autowired
    private DemoService demoService;

    @Autowired
    private LoginmonitorService loginmonitorService;

    @RequestMapping(value = "/test")
    public String test(){
        return "SUCCESS!";
    }

    static int r = 0;
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 5; j++) {
                        r++;
                        System.err.println("**" + r);
                        if(r>=10){
                            System.exit(1);
                        }
                    }
                }
            });
            thread1.setName("我是线程" + i);
            thread1.start();
        }
    }

    @GetMapping(value = "/demo")
    public String demo(){
        List<Demo> list = demoService.getAll();
        System.err.println(list.toString());
        return list.toString();
    }

    @GetMapping(value = "/login")
    public String login(){
        List<Loginmonitor> list = loginmonitorService.getAll();
        System.err.println(list.toString());
        return list.toString();
    }

    @PostMapping(value = "/add")
    public String add(@RequestBody List<Loginmonitor> list) {
        System.err.println(list.toString());
        loginmonitorService.addList(list);
        return "SUCCESS!";
    }

    @Autowired(required = false)
    private HttpForestClient client;

    @RequestMapping(value = "client")
    public String clientTest(){
        String str = client.simpleGet();
        log.error(str);
        return str;
    }

    @RequestMapping(value = "clientRESTful")
    public String clientRESTful(String id){
        String str = client.restfulGet(id);
        log.error(str);
        return str;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "redis")
    public String redis(){
        redisTemplate.opsForValue().set("123", "666666", 3000, TimeUnit.SECONDS);
        return "";
    }





}
