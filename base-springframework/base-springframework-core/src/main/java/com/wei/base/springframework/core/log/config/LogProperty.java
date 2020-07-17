package com.wei.base.springframework.core.log.config;

import ch.qos.logback.core.PropertyDefinerBase;
import com.wei.base.springframework.util.StringUtil;

/**
 * 日志配置文件 由于日志会在spring之前先进行加载没办法通过spring来配置动态化
 *
 * @author : weierming
 * @date : 2020/7/10
 */
public class LogProperty extends PropertyDefinerBase {

    private static final String LOG_PATH = System.getProperty("user.dir");

    @Override
    public String getPropertyValue() {
        return StringUtil.substringAfterLast(LOG_PATH, "/");
    }
}