package com.gupiao.xjob;

import com.gupiao.service.StockService;
import com.gupiao.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 更新全量行业交易数据  industry_transactions
 */
@Configuration //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling //2.开启定时任务
public class UpdateIndustryDataTask {

    @Autowired
    StockService stockService;

    //@Scheduled(cron="0 */1 * * * *")
    private void configureTasks(){

        //获取处理时间，正常是day-1
        String date = DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE4);
        date = DateUtils.dateAddDays(date,DateUtils.DATE_FORMATE4,-1L);
        stockService.getIndustryTransactions(date);
    }

}
