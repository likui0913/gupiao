package com.gupiao.xjob;

import com.gupiao.aop.WebLoadAop;
import com.gupiao.generator.domain.SysSetting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;

/**
 * 清理过期session信息
 */
@Configuration //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling //2.开启定时任务
@Slf4j
@EnableAsync
public class CleanTask {

    /**
     * 清理过期session信息
     */
    @Scheduled(fixedDelay = 1000 * 60 * 30)
    @Async(value="asyncExecutor")
    public void cleanTimeoutSession(){
        try{

            Long now = System.currentTimeMillis();
            WebLoadAop.cache.entrySet().removeIf(stringLongEntry -> (now - stringLongEntry.getValue()) > 1000 * 60 * 30);

        }catch (Exception e){
            log.error("cleanTimeoutSession 出现错误！",e);
        }
    }

}
