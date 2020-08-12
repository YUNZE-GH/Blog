package com.gh.blog.service;

import com.gh.blog.entity.Demo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/12 10:57
 */
public interface DemoService {
    List<Demo> getAll();
}
