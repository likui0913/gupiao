package com.gupiao.xjob;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * 更新全量行业交易数据  industry_transactions
 */
@Configuration //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling //2.开启定时任务
public class UpdateIndustryDataTask {

    //@Scheduled(cron="0/5 * * * * *")
    private void configureTasks(){
        System.out.println("执行静态定时任务时间："+ LocalDateTime.now());
    }

}
