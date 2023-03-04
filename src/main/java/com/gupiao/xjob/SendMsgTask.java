package com.gupiao.xjob;

import com.gupiao.service.SendDingDingMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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

}
