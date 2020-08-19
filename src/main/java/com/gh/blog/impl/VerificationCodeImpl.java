package com.gh.blog.impl;

import com.gh.blog.dao.blog.VerificationCodeDao;
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
import java.util.Random;
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
     *
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
        } else {
            VerificationCode object = new VerificationCode();
            int verificationCode = new Random().nextInt(999999) + 100000;
            String message = smsSending.sendSMS(phone, registerSMSTemplate, String.valueOf(verificationCode));
            log.info("==========>信息回执：" + message);
            JSONObject result = JSONObject.fromObject(message);
            Object code = result.get("Code");
            if (publicUtils.isNotEmpty(code) && code.toString().equals("OK")) {
                // 将验证码和回执存库
                object.setBizid(result.getString("BizId"));
                // 验证码存缓存，号码为key，验证码为value，10分钟过期
                redisTemplate.opsForValue().set(phone, String.valueOf(verificationCode), 60 * 10, TimeUnit.SECONDS);
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
            object.setVerification(String.valueOf(verificationCode));

            log.info(object.toString());
            dao.addOneInfo(object);
            json.put("success", false);
            json.put("message", message);
            return json.toString();
        }
    }
}
