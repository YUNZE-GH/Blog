package com.gh.blog.service;

import com.gh.blog.entity.Loginmonitor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/12 10:59
 */
public interface LoginmonitorService {
    List<Loginmonitor> getAll();

    void addList(List<Loginmonitor> list);
}
