package com.gh.blog.service;

import com.gh.blog.entity.Accountinfo;

import java.util.List;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/12 10:57
 */
public interface AccountinfoService {
    List<Accountinfo> getAll();

    Accountinfo getOne(String sid);

    String getOne(String phone, String password);
}
