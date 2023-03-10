package com.gupiao.xjob;

import com.google.gson.Gson;
import com.gupiao.enums.LogSwitchEnums;
import com.gupiao.generator.domain.SysSetting;
import com.gupiao.generator.mapper.SysSettingMapper;
import com.gupiao.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Locale;

/**
 * 日志类更新
 */
@Configuration
@Slf4j
public class ConfigTask {

    @Autowired
    private SysSettingMapper sysSettingMapper;

    //@Scheduled(fixedRate=2000)  此任务因为和其他定时器任务冲突，不能按时执行，所以由单线程代替，禁止开启
    @Async(value="asyncExecutor")
    public void updateConfig(){

        try{
        }catch (Exception e){
            log.error("ConfigTask--updateConfig 任务出错",e);
        }

    }

    /**
     * 处理日志
     * @param key
     */
    public void updateLogConfig(String key){

        SysSetting setting = sysSettingMapper.selectByCode(key);
        if(null == setting){
            LogUtil.deleteLogSwitchByKey(key);
            return;
        }
         if(setting.getSysValue().toLowerCase().equals("false") ||
                 setting.getSysValue().toLowerCase().equals("0")){
             LogUtil.setLogSwitchByKey(key,Boolean.FALSE);
             //log.info("set key=" + key + " value=false");
         }else if(setting.getSysValue().toLowerCase().equals("true") ||
                 setting.getSysValue().toLowerCase().equals("1")){
             LogUtil.setLogSwitchByKey(key,Boolean.TRUE);
             //log.info("set key=" + key + " value=true");
         }
    }

}
