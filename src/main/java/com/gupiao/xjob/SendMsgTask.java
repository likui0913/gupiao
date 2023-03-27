package com.gupiao.xjob;

import com.gupiao.generator.domain.SysSetting;
import com.gupiao.generator.mapper.SysSettingMapper;
import com.gupiao.service.SendDingDingMsgService;
import com.gupiao.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 发送消息任务
 */
@Configuration
@EnableScheduling
@Slf4j
public class SendMsgTask {

    @Autowired
    private SendDingDingMsgService sendDingDingMsgService;

    @Autowired
    private SysSettingMapper sysSettingMapper;

    //@Scheduled(fixedDelay = 1000*60*30)//单位是毫秒
    public void sendDingDingMsg(){

        try{
            log.info("SendMsgTask--sendDingDingMsg 开始执行");
            sendDingDingMsgService.sendLastNDaysLowPriceCode(7,0,25,25);
            log.info("SendMsgTask--sendDingDingMsg 结束执行");
        }catch (Exception e){
            log.error("SendMsgTask--sendDingDingMsg 任务出错",e);
        }

    }

    @Scheduled(fixedDelay = 1000*60)//单位是毫秒
    @Async(value="asyncExecutor")
    public void sengAutoStockMsg(){

        try{

            //log.info("SendMsgTask--sengAutoStockMsg 开始执行");

            String key = "autoNotifyStockData";
            SysSetting setting = sysSettingMapper.selectByCode(key);
            if(null == setting){
                log.info("SendMsgTask--sengAutoStockMsg 配置未开启，结束执行");
                return;
            }

            if(setting.getSysValue().equalsIgnoreCase("false") ||
                    setting.getSysValue().equalsIgnoreCase("0")){
                log.info("SendMsgTask--sengAutoStockMsg 配置未开启，结束执行");
            }else if(setting.getSysValue().equalsIgnoreCase("true") ||
                    setting.getSysValue().equalsIgnoreCase("1")){
                sendDingDingMsgService.sendAutoStockMsg();
                //log.info("SendMsgTask--sengAutoStockMsg 结束执行");
            }

        }catch (Exception e){
            log.error("SendMsgTask--sengAutoStockMsg 任务出错",e);
        }

    }

}
