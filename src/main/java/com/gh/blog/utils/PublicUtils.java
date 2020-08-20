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

    public static void main(String[] args) {
        PublicUtils p = new PublicUtils();
        Object s = "15346646668";
        System.err.println(p.validateMobilePhone(s.toString()));
    }

    public boolean validateMobilePhone(Object phone) {
        String str = phone.toString();
        Pattern pattern = Pattern.compile("^[1]\\d{10}$");
        return pattern.matcher(str).matches();
    }

    public boolean isNotEmpty(Object obj) {
        if (obj == null || String.valueOf(obj).length() == 0) {
            return false;
        }
        return true;
    }
}
