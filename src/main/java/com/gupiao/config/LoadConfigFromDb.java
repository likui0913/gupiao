package com.gupiao.config;

import com.gupiao.generator.domain.SysSetting;
import com.gupiao.generator.mapper.SysSettingMapper;
import com.gupiao.service.thread.ConfigUpdateThread;
import com.gupiao.util.StaticValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class LoadConfigFromDb {

    @Autowired
    SysSettingMapper sysSettingMapper;

    @PostConstruct
    public void init(){

        ConfigUpdateThread thread = new ConfigUpdateThread();
        thread.setSysSettingMapper(sysSettingMapper);
        thread.start();

        //加载数据访问路径配置
        SysSetting setting = sysSettingMapper.selectByCode("dataUrlPathSetting");
        if(null == setting){
            throw new RuntimeException("dataUrlPathSetting 从数据库读取失败，此配置不能为null");
        }
        if( StaticValue.DATA_URL_REMOTE.equals(setting.getSysValue()) ){
            StaticValue.DATA_URL_PATH_SETTING = "remote";
        }else if( StaticValue.DATA_URL_LOCAL.equals(setting.getSysValue()) ){
            StaticValue.DATA_URL_PATH_SETTING = "local";
        }
        log.info("DATA_URL_PATH_SETTING="+setting.getSysValue());

        //加载图片缓存路径
        SysSetting settingPicPath = sysSettingMapper.selectByCode("picPath");
        if(null == settingPicPath){
            throw new RuntimeException("picPath 从数据库读取失败，此配置不能为null");
        }
        StaticValue.PIC_PATH_SETTING = settingPicPath.getSysValue();
        log.info("PIC_PATH_SETTING="+settingPicPath.getSysValue());

    }

}
