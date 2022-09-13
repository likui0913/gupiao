package com.gupiao.xjob;

import com.gupiao.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * 更新全量股票基础信息，包含股票code，名称等 表stock_detail
 */
@Configuration //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling //2.开启定时任务
@Slf4j
public class UpdateFullStockUpdateTask {

    @Autowired
    StockService stockService;

    @Scheduled(cron="0 */1 * * * *")
    private void configureTasks(){
        try{
            log.info("开始刷新全部股票信息,date:" + LocalDateTime.now());
            this.getAllStock();
            log.info("完成刷新全部股票信息,date:" + LocalDateTime.now());
        }catch (Exception e){

        }
    }

    private void getAllStock(){
        stockService.updateAllStockMsg(1);
    }



}
