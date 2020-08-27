package com.gh.blog.controller;

import com.gh.blog.entity.AccountInfo;
import com.gh.blog.service.AccountInfoService;
import com.gh.blog.utils.ImageCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/17 13:58
 */
@RestController
@RequestMapping(value = "account",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "account", tags = "AccountinfoController", description = "账号管理模块接口")
public class AccountInfoController {

    private Log log = LogFactory.getLog(this.getClass().getName());

    @Autowired
    private AccountInfoService service;

    /**
     * 使用SessionId查询单条账号信息信息
     * @param sid
     * @return
     */
    @ApiOperation(value = "getAccountInfoBySid",notes = "使用SessionId查询单条账号信息信息")
    @ApiImplicitParam(name = "sid", value = "SessionId", required = true,paramType = "path", dataType = "String")
    @GetMapping(value = "/getAccountInfoBySid/{sid}")
    public String getAccountInfoBySid(@PathVariable(required = true) String sid) {
        AccountInfo bo = service.getOne(sid);
        JSONObject json = new JSONObject();
        json.put("status", "200");
        if (bo == null) {
            json.put("success", false);
            json.put("message", "账号已过期，请重新登录！");
            return json.toString();
        } else {
            json.put("success", true);
            json.put("message", "查询成功！");
            json.put("data", bo);
            return json.toString();
        }
    }

    /**
     * 使用手机号和密码登录校验
     * @param phone
     * @param password
     * @return
     */
    @ApiOperation(value = "loginCheck",notes = "使用手机号和密码进行登录校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true,paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true,paramType = "path", dataType = "String")
    })
    @GetMapping(value = "/loginCheck/{phone}/{password}")
    public String loginCheck(
            @PathVariable(required = true) String phone,
            @PathVariable(required = true) String password,
            HttpServletRequest request) {
        String uuid = service.getOne(phone, password);
        JSONObject json = new JSONObject();
        json.put("status", "200");
        if (uuid == null || uuid.isEmpty()) {
            json.put("success", false);
            json.put("message", "手机号或密码错误！");
            return json.toString();
        } else {
            json.put("success", true);
            json.put("message", "登录校验成功！");
            json.put("sid", uuid);
            request.getSession().setAttribute("sid", uuid);
            log.error(json.toString());
            return json.toString();
        }
    }

    @ApiOperation(value = "RegisterCheck",notes = "使用手机号和验证码进行校验及账号注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{\"username\":\"昵称\",\"phone\":\"手机号\",\"password\":\"密码\",\"code\":\"验证码\"}",
                    required = true,paramType = "body", dataType = "JSON"),
    })
    @PostMapping(value = "/RegisterCheck")
    public String getSid(@RequestBody JSONObject json){
        String result = service.registerCheck(json);
        return result;
    }


    @ApiOperation(value = "generateImageCode",notes = "随机生成图片验证码")
    @GetMapping(value = "/generateImageCode")
    public void generateImageCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        BufferedImage verifyImg = new BufferedImage(100, 30, BufferedImage.TYPE_INT_BGR);
        String randomText = ImageCode.drawRandowmText(100, 30, verifyImg);
        // 验证码存session或redis
        HttpSession session = request.getSession();
        session.setAttribute("imageCode", randomText);
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(verifyImg, "png", os);
        os.flush();
        os.close();
    }

}
