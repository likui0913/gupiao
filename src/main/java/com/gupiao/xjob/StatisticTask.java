package com.gupiao.xjob;

import com.gupiao.generator.domain.SysSetting;
import com.gupiao.service.StatisticService;
import com.gupiao.util.StaticValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 处理统计任务
 */
@Configuration
@EnableScheduling
@Slf4j
public class StatisticTask {

    @Autowired
    StatisticService statisticService;

    //@Scheduled(cron="0/5 * * * * *")
    private void configureTasks(){

        log.info("StatisticTask 定时任务启动");
        statisticService.cpmputeAllStackMovingAverage();

    }

}
