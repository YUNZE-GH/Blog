package com.gh.blog.impl;

import com.gh.blog.dao.blog.AccountinfoDao;
import com.gh.blog.entity.Accountinfo;
import com.gh.blog.service.AccountinfoService;
import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/17 13:51
 */
@Service
public class AccountinfoImpl implements AccountinfoService {
    private Log log = LogFactory.getLog(this.getClass().getName());
    @Autowired
    private AccountinfoDao dao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Accountinfo> getAll() {
        return null;
    }

    @Override
    public Accountinfo getOne(String sid) {
        ValueOperations operations = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(sid)) {
            String json = (String) operations.get(sid);
            Accountinfo bo = new Gson().fromJson(json, Accountinfo.class);
            log.error("=====================>取缓存数据");
            bo.setPassword(null);
            return bo;
        }
        return null;
    }

    @Override
    public String getOne(String phone, String password) {
        Accountinfo bo = dao.getOne(phone);
        if (StringUtils.isEmpty(bo.getId()) && StringUtils.isEmpty(password)) {
            return null;
        } else if (bo.getPassword().equals(password)){
            bo.setPassword(null);
            String uuid = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(uuid, JSONObject.fromObject(bo).toString());
            return uuid;
        } else {
            return null;
        }
    }

}
