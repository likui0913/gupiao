package com.gupiao.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 日志开关类
 */
public class LogUtil {

    public static Map<String,Boolean> logSwitch = new HashMap<>();

    /**
     * 设置日志开关
     *
     * @param key
     * @param value
     */
    public static void setLogSwitchByKey(String key,Boolean value){
        logSwitch.put(key,value);
    }

    /**
     * 根据key获取配置
     *
     * @param key
     * @param defValue
     */
    public static Boolean getLogSwitchByKey(String key,Boolean defValue){
        return logSwitch.getOrDefault(key, defValue);
    }

    public static void deleteLogSwitchByKey(String key){
        logSwitch.remove(key);
    }

}
