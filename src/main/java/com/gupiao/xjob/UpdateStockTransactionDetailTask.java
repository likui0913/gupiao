package com.gupiao.xjob;

import com.gupiao.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * 更新全量股票交易数据 stock_market_data
 */
@Configuration //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling //2.开启定时任务
public class UpdateStockTransactionDetailTask {

    @Autowired
    StockService stockService;

    @Scheduled(cron="* * * */1 * *")
    private void configureTasks(){
        stockService.getAllStockYesterdayData(5);
    }

}
