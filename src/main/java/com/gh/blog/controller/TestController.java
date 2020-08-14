package com.gh.blog.controller;

import com.gh.blog.entity.Demo;
import com.gh.blog.entity.Loginmonitor;
import com.gh.blog.service.DemoService;
import com.gh.blog.service.LoginmonitorService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/11 21:10
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {

    @Autowired
    private DemoService demoService;

    @Autowired
    private LoginmonitorService loginmonitorService;

    @RequestMapping(value = "/test")
    public String test(){
        return "SUCCESS!";
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
}
