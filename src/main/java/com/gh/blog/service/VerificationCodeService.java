package com.gh.blog.service;

import com.gh.blog.entity.VerificationCode;

import java.util.List;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/12 10:57
 */
public interface VerificationCodeService {
    List<VerificationCode> getAll();

    String saveReceiptSendSmsMessage(VerificationCode bo, String registerSMSTemplate);
}
