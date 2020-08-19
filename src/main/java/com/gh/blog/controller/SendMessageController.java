package com.gh.blog.controller;

import com.gh.blog.utils.AliCloud_SMS_Sending;
import com.gh.blog.utils.PublicUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/19 9:50
 */
@RestController
@RequestMapping(value = "sendMessage", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "SendMessageController", description = "信息发送管理接口")
public class SendMessageController {
    private Log log = LogFactory.getLog(this.getClass().getName());

    @Autowired
    private PublicUtils publicUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AliCloud_SMS_Sending smsSending;

    @Value("${registerSMSTemplate}")
    private String registerSMSTemplate;

    @ApiOperation(value = "sendSmsMessage",notes = "发送账号注册短信验证码")
    @ApiImplicitParam(name = "phone", value = "电话号码", required = true,paramType = "query", dataType = "String")
    @GetMapping(value = "sendSmsMessage")
    public String sendSmsMessage(@RequestParam(required = true) String phone){
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
            int verificationCode = new Random().nextInt(999999) + 100000;
            String message = smsSending.sendSMS(phone, registerSMSTemplate, String.valueOf(verificationCode));
            JSONObject result = JSONObject.fromObject(message);
            Object code = result.get("Code");
            if (publicUtils.isNotEmpty(code) && code.toString().equals("OK")) {
                // 将验证码和回执存库
                redisTemplate.opsForValue().set(phone, String.valueOf(verificationCode), 60, TimeUnit.SECONDS);
                json.put("success", true);
                json.put("message", message);
            }
            json.put("success", false);
            json.put("message", message);
            return json.toString();
        }
    }
}
