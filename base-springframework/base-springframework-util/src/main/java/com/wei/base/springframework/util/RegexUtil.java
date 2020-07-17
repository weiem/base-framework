package com.wei.base.springframework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则匹配工具
 *
 * @author admin
 */
public class RegexUtil {

    public static boolean matcher(String matcher, String pattern) {
        Pattern p = Pattern.compile(matcher);
        Matcher m = p.matcher(pattern);
        return m.matches();
    }
}
