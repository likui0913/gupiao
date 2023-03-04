package com.gupiao.service.thread;

import com.gupiao.enums.LogSwitchEnums;
import com.gupiao.generator.domain.SysSetting;
import com.gupiao.generator.mapper.SysSettingMapper;
import com.gupiao.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 更新配置类，因为时效问题，不使用定时器任务执行
 */
@Slf4j
public class ConfigUpdateThread extends Thread{

    @Autowired
    private SysSettingMapper sysSettingMapper;

    public SysSettingMapper getSysSettingMapper() {
        return sysSettingMapper;
    }

    public void setSysSettingMapper(SysSettingMapper sysSettingMapper) {
        this.sysSettingMapper = sysSettingMapper;
    }

    @Override
    public void run() {

        while(true){
            try{
                updateConfig();
                Thread.sleep(1000*5);//每隔5秒更新1次
            }catch (Exception e){
                log.error("ConfigUpdateThread--updateConfig error",e);
            }
        }

    }

    public void updateConfig(){

        try{
            updateLogConfig(LogSwitchEnums.STOCK_MSG.getName());
            updateLogConfig(LogSwitchEnums.STOCK_TRADE.getName());
            updateLogConfig(LogSwitchEnums.STOCK_STATIC.getName());
            updateLogConfig(LogSwitchEnums.STOCK_STATIC_TWO_DAY.getName());
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
