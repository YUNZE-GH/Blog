package com.gh.blog.controller;

import com.gh.blog.entity.VerificationCode;
import com.gh.blog.service.VerificationCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


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

    @Value("${registerSMSTemplate}")
    private String registerSMSTemplate;

    @Autowired
    private VerificationCodeService verificationCodeService;


    @ApiOperation(value = "sendSmsMessage", notes = "发送账号注册短信验证码")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "phone", value = "电话号码", required = true, paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "imageCode", value = "图片验证码", required = true, paramType = "query", dataType = "String")
    })

    @GetMapping(value = "sendSmsMessage")
    public String sendSmsMessage(
            @RequestParam(required = true) String phone,
            @RequestParam(required = true) String imageCode,
            HttpServletRequest request) {
        HttpSession session = request.getSession();
        String imageCodeCache = (String) session.getAttribute("imageCode");
        // 校验随机图片验证码是否正确
        if (imageCode.equals(imageCodeCache)) {
            VerificationCode code = new VerificationCode();
            code.setPhone(phone);
            // 发送账号注册短信验证码并将回执存库及存redis
            String result = verificationCodeService.saveReceiptSendSmsMessage(code, registerSMSTemplate);
            return result;
        }
        JSONObject json = new JSONObject();
        json.put("status", "200");
        json.put("success", false);
        json.put("message", "图片验证码输入错误！");
        return json.toString();
    }
}
