package com.wei.base.springframework.core.log.config;

import ch.qos.logback.core.PropertyDefinerBase;

/**
 * 日志配置文件 由于日志会在spring之前先进行加载没办法通过spring来配置动态化
 *
 * @author : weierming
 * @date : 2020/7/10
 */
public class LogProperty extends PropertyDefinerBase {

    //系统变量配置
    private static final String OS_NAME = "os.name";

    //系统变量配置
    private static final String LINUX = "linux";

    //linux日志目录
    private static final String LOG_PATH = "logs";

    //其他系统日志目录
    private static final String OTHER_LOG_PATH = "target";

    @Override
    public String getPropertyValue() {
        if (System.getProperty(OS_NAME).toLowerCase().indexOf(LINUX) >= 0) {
            return LOG_PATH;
        }

        return OTHER_LOG_PATH;
    }
}