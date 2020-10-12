package com.gh.blog.dao.blog;

import com.gh.blog.entity.AccountInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/12 10:57
 */
@Repository
public interface AccountInfoDao {
    List<AccountInfo> getAll();

    AccountInfo getOne(String phone);

    void registerOneAccountInfo(AccountInfo bo);

    int uniqueCheck(AccountInfo bo);

    void batchInsert(List<AccountInfo> list);
}
