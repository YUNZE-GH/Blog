package com.gh.blog.service;

import com.gh.blog.entity.AccountInfo;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/12 10:57
 */
public interface AccountInfoService {
    List<AccountInfo> getAll();

    AccountInfo getOne(String sid);

    String getOne(String phone, String password);

    String registerCheck(JSONObject json);
}
