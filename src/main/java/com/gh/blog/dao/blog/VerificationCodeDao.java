package com.gh.blog.dao.blog;

import com.gh.blog.entity.VerificationCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/12 10:57
 */
@Repository
public interface VerificationCodeDao {
    List<VerificationCode> getAll();

    void addOneInfo(VerificationCode bo);
}
