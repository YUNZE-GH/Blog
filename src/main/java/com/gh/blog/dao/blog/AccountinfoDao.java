package com.gh.blog.dao.blog;

import com.gh.blog.entity.Accountinfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/12 10:57
 */
@Repository
public interface AccountinfoDao {
    List<Accountinfo> getAll();

    Accountinfo getOne(String phone);
}
