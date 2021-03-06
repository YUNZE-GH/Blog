package com.gh.blog.impl;

import com.gh.blog.dao.blog.AccountInfoDao;
import com.gh.blog.dao.blog.VerificationCodeDao;
import com.gh.blog.entity.AccountInfo;
import com.gh.blog.entity.VerificationCode;
import com.gh.blog.service.VerificationCodeService;
import com.gh.blog.utils.AliCloud_SMS_Sending;
import com.gh.blog.utils.PublicUtils;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/19 15:39
 */
@Service
public class VerificationCodeImpl implements VerificationCodeService {
    private Log log = LogFactory.getLog(this.getClass().getName());

    @Autowired
    private VerificationCodeDao dao;

    @Autowired
    private AccountInfoDao accountinfoDao;

    @Autowired
    private PublicUtils publicUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AliCloud_SMS_Sending smsSending;

    @Override
    public List<VerificationCode> getAll() {
        return null;
    }

    /**
     * 发送账号注册短信验证码，并将回执存库
     * @param bo
     * @param registerSMSTemplate
     * @return
     */
    @Override
    public String saveReceiptSendSmsMessage(VerificationCode bo, String registerSMSTemplate) {
        String phone = bo.getPhone();
        JSONObject json = new JSONObject();
        json.put("status", "200");
        if (phone == null || phone.isEmpty()) {
            json.put("success", false);
            json.put("message", "请输入手机号码！");
            return json.toString();
        } else if (!publicUtils.validateMobilePhone(phone)) {
            json.put("success", false);
            json.put("message", "请输入正确手机号码！");
            return json.toString();
        } else if (redisTemplate.hasKey(phone)) {
            json.put("success", false);
            json.put("message", "请不要连续发送验证码！");
            return json.toString();
        } else if (!phoneUniqueCheck(phone)) {
            json.put("success", false);
            json.put("message", "该手机号已经注册账号！");
            return json.toString();
        } else {
            VerificationCode object = new VerificationCode();
            // 生成1-999999之间的随机数,进行6位数补全
            String verificationCode = String.format("%06d" ,(int) (Math.random() * 999999 + 1));
            // 发送验证码
            // todo 暂时关闭手机验证码发送功能，验证码可从redis查询
//            String message = smsSending.sendSMS(phone, registerSMSTemplate, verificationCode);
            String message = "{\"Message\":\"OK\",\"RequestId\":\"5715C906-66EE-4E34-9E9D-DEA10B9BEC01\",\"BizId\":\"398701798878411077^0\",\"Code\":\"OK\"}";
            JSONObject result = JSONObject.fromObject(message);
            Object code = result.get("Code");
            if (publicUtils.isNotEmpty(code) && code.toString().equals("OK")) {
                // 将验证码和回执存库
                object.setBizid(result.getString("BizId"));
                // 验证码存缓存，号码为key，验证码为value，60秒过期
                redisTemplate.opsForValue().set(phone, verificationCode, 60, TimeUnit.SECONDS);
                json.put("success", true);
                json.put("message", message);
            }
            object.setOid(UUID.randomUUID().toString());
            object.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            object.setPhone(phone);
            object.setCode(result.getString("Code"));

            object.setRequestid(result.getString("RequestId"));
            object.setMessage(result.getString("Message"));
            object.setEmail("");
            object.setVerification(verificationCode);

            log.info(object.toString());
            dao.addOneInfo(object);
            json.put("success", true);
            json.put("message", message);
            return json.toString();
        }
    }

    /**
     * 校验该手机号是否已经注册
     * @param phone
     * @return
     */
    public boolean phoneUniqueCheck(String phone) {
        AccountInfo bo = new AccountInfo();
        bo.setPhone(phone);
        int result = accountinfoDao.uniqueCheck(bo);
        if (result > 0) {
            return false;
        }
        return true;
    }
}
