package com.gh.blog.dao.monitor;

import com.gh.blog.entity.Loginmonitor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/12 10:59
 */
@Repository
public interface LoginmonitorDao {
    List<Loginmonitor> getAll();
}
