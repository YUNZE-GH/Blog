package com.gh.blog.impl;

import com.gh.blog.dao.monitor.LoginmonitorDao;
import com.gh.blog.entity.Loginmonitor;
import com.gh.blog.service.LoginmonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/12 11:02
 */
@Service
public class LoginmonitorImpl implements LoginmonitorService {
    @Autowired
    private LoginmonitorDao dao;

    @Override
    public List<Loginmonitor> getAll() {
        return dao.getAll();
    }

    @Override
    public void addList(List<Loginmonitor> list) {
        dao.addList(list);
    }
}
