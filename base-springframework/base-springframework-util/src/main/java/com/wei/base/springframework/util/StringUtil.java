package com.wei.base.springframework.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串工具类
 *
 * @author admin
 */
public class StringUtil extends StringUtils {

    public static final char UNDERLINE = '_';

    /**
     * 驼峰格式字符串转换为下划线格式字符串
     * <pre>
     * camelToUnderline("userName") = "user_name";
     * </pre>
     *
     * @param param 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String camelToUnderline(String param) {
        if (isBlank(param)) {
            return "";
        }

        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 驼峰格式字符串转换为下划线格式字符串
     * <pre>
     * camelToUnderline("userName") = "user_name";
     * </pre>
     *
     * @param param 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String[] camelToUnderline(String[] param) {
        if (param == null) {
            return null;
        }

        int length = param.length;
        String[] array = new String[length];
        for (int i = 0; i < length; i++) {
            array[i] = camelToUnderline(param[i]);
        }

        return array;
    }

    /**
     * 下划线格式字符串转换为驼峰格式字符串
     *
     * <pre>
     * underlineToCamel("user_name") = "userName";
     * </pre>
     */
    public static String underlineToCamel(String param) {
        if (isBlank(param)) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线格式字符串转换为大驼峰格式字符串
     *
     * <pre>
     * underlineToCamel("user_name") = "UserName";
     * </pre>
     */
    public static String underlineToBigCamel(String param) {
        if (isBlank(param)) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                if (i == 0) {
                    c = Character.toUpperCase(c);
                }
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 拆分字符串为list
     *
     * @param str            需要拆分的字符串
     * @param separatorChars 拆分格式
     * @return 返回拆分的字符串集合
     */
    public static List<String> splitList(final String str, final String separatorChars) {
        if (isBlank(str)) {
            return Lists.newArrayList();
        }

        return splitList(str, separatorChars, -1, false);
    }

    /**
     * 拆分字符串为list
     *
     * @param str               需要拆分的字符串
     * @param separatorChars    拆分格式
     * @param max
     * @param preserveAllTokens
     * @return
     */
    public static List<String> splitList(final String str, final String separatorChars, final int max, final boolean preserveAllTokens) {
        if (str == null) {
            return null;
        }
        final int len = str.length();
        if (len == 0) {
            return Lists.newArrayList();
        }
        final List<String> list = new ArrayList<>();
        int sizePlus1 = 1;
        int i = 0, start = 0;
        boolean match = false;
        boolean lastMatch = false;
        if (separatorChars == null) {
            // Null separator means use whitespace
            while (i < len) {
                if (Character.isWhitespace(str.charAt(i))) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        } else if (separatorChars.length() == 1) {
            // Optimise 1 character case
            final char sep = separatorChars.charAt(0);
            while (i < len) {
                if (str.charAt(i) == sep) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        } else {
            // standard case
            while (i < len) {
                if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        }
        if (match || preserveAllTokens && lastMatch) {
            list.add(str.substring(start, i));
        }

        return list;
    }

    /**
     * 拆分字符串为list
     *
     * @param str 需要拆分的字符串
     * @return 返回按照逗号分隔的字符串集合
     */
    public static List<String> splitList(String str) {
        return splitList(str, ",");
    }

    /**
     * 获取typeName对应的class类名
     *
     * @param typeName typeName
     * @return 类名
     */
    public static String getClassName(String typeName) {
        String[] split = StringUtil.split(typeName, ".");
        if (split == null || split.length <= 0) {
            return typeName;
        }

        return split[split.length - 1];
    }
}