package com.gh.blog.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/19 14:42
 */
@Component
public class PublicUtils {

    public boolean validateMobilePhone(String phone) {
        Pattern pattern = Pattern.compile("^[1]\\d{10}$");
        return pattern.matcher(phone).matches();
    }

    public boolean isNotEmpty(Object obj) {
        if (obj == null || String.valueOf(obj).length() == 0) {
            return false;
        }
        return true;
    }
}
