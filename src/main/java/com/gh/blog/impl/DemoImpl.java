package com.gh.blog.impl;

import com.gh.blog.dao.blog.DemoDao;
import com.gh.blog.entity.Demo;
import com.gh.blog.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/12 11:01
 */
@Service
public class DemoImpl implements DemoService {
    @Autowired
    private DemoDao dao;

    @Override
    public List<Demo> getAll() {
        return dao.getAll();
    }
}
