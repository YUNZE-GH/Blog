package com.gh.blog.impl;

import com.gh.blog.dao.blog.AccountInfoDao;
import com.gh.blog.entity.AccountInfo;
import com.gh.blog.service.AccountInfoService;
import com.gh.blog.utils.PublicUtils;
import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/17 13:51
 */
@Service
public class AccountInfoImpl implements AccountInfoService {
    private Log log = LogFactory.getLog(this.getClass().getName());
    @Autowired
    private AccountInfoDao dao;

    @Autowired
    private PublicUtils publicUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<AccountInfo> getAll() {
        return null;
    }

    @Override
    public AccountInfo getOne(String sid) {
        ValueOperations operations = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(sid)) {
            String json = (String) operations.get(sid);
            AccountInfo bo = new Gson().fromJson(json, AccountInfo.class);
            log.error("=====================>取缓存数据");
            bo.setPassword(null);
            return bo;
        }
        return null;
    }

    @Override
    public String getOne(String phone, String password) {
        AccountInfo bo = dao.getOne(phone);
        if (StringUtils.isEmpty(bo.getId()) && StringUtils.isEmpty(password)) {
            return null;
        } else if (bo.getPassword().equals(password)) {
            bo.setPassword(null);
            String uuid = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(uuid, JSONObject.fromObject(bo).toString());
            return uuid;
        } else {
            return null;
        }
    }

    @Override
    public String registerCheck(JSONObject object) {
        JSONObject json = new JSONObject();
        json.put("status", "200");

        AccountInfo bo = new AccountInfo();
        Object username = object.get("username");
        Object phone = object.get("phone");
        Object password = object.get("password");
        Object code = object.get("code");
        if (!publicUtils.isNotEmpty(username)) {    // 用户名是否为空
            json.put("success", false);
            json.put("message", "请输入用户名！");
            return json.toString();
        } else if (!publicUtils.isNotEmpty(phone)) {    // 手机号是否为空
            json.put("success", false);
            json.put("message", "请输入手机号码！");
            return json.toString();
        } else if (!publicUtils.validateMobilePhone(phone)) {   // 手机号是否为异常
            json.put("success", false);
            json.put("message", "请输入正确手机号码！");
            return json.toString();
        } else if (!publicUtils.isNotEmpty(password)) {      // 密码是否为空
            json.put("success", false);
            json.put("message", "请输入密码！");
            return json.toString();
        } else if (!publicUtils.isNotEmpty(code)) {      // 验证码是否为空
            json.put("success", false);
            json.put("message", "请输入验证码！");
            return json.toString();
        } else if (redisTemplate.hasKey(phone)) {   // 缓存中该手机号的验证码是否过期（10分钟）
            // 校验验证码
            Object redis_code = redisTemplate.opsForValue().get(phone);
            if (publicUtils.isNotEmpty(redis_code)) {
                // 验证码是否正确
                if (redis_code.toString().equals(code)) {
                    // 账号信息存库
                    String time = new SimpleDateFormat("yyyy-MM-dd HH:mm;ss").format(new Date());
                    bo.setOid(UUID.randomUUID().toString());
                    bo.setUsername(username.toString());
                    bo.setPhone(phone.toString());
                    bo.setPassword(password.toString());
                    bo.setCreatetime(time);
                    bo.setUpdatetime(time);
                    dao.registerOneAccountInfo(bo);
                    json.put("success", true);
                    json.put("message", "账号注册成功！");
                    return json.toString();
                } else {
                    json.put("success", false);
                    json.put("message", "验证码错误！");
                    return json.toString();
                }
            }
        }
        json.put("success", false);
        json.put("message", "请重新获取验证码！");
        return json.toString();
    }
}
