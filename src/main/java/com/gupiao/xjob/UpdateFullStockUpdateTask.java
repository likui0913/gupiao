package com.gupiao.xjob;

import com.gupiao.generator.domain.SysSetting;
import com.gupiao.service.StockService;
import com.gupiao.service.UpdateStockDailySaleService;
import com.gupiao.service.UpdateStockListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 更新全量股票基础信息，包含股票code，名称 表stock_detail
 */
@Configuration //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling //2.开启定时任务
@Slf4j
@EnableAsync
public class UpdateFullStockUpdateTask {

    @Autowired
    UpdateStockListService updateAllStockMsg;

    @Autowired
    UpdateStockDailySaleService updateStockDailySaleService;

    ReentrantLock lock = new ReentrantLock();

    //@Scheduled(fixedDelay = 1000*60*30)
    //@Async
    public void updateStockSaleData(){
        try{
            log.info("开始增量刷新全部股票历史交易信息,date:" + LocalDateTime.now());
            updateStockDailySaleService.updateAllStockDailySale();
            log.info("结束增量刷新全部股票历史交易信息,date:" + LocalDateTime.now());
        }catch (Exception e){
            log.error("updateStockSaleData 出现错误！",e);
        }
    }

    //每个6个小时更新1次
    //@Scheduled(fixedDelay = 1000*60*60*6)
    //@Async
    public void updateAllStockMsg(){
        try{
            log.info("开始刷新全部股票基础信息,date:" + LocalDateTime.now());
            this.getAllStock();
            log.info("完成刷新全部股票基础信息,date:" + LocalDateTime.now());
        }catch (Exception e){

        }
    }

    private void getAllStock(){
        updateAllStockMsg.updateAllStockMsg();
    }

}
