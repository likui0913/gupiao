package com.gupiao.xjob;

import com.gupiao.generator.domain.SysSetting;
import com.gupiao.generator.mapper.SysSettingMapper;
import com.gupiao.service.StockService;
import com.gupiao.service.UpdateStockDailySaleService;
import com.gupiao.service.UpdateStockListService;
import com.gupiao.service.UpdateStockNetFlowService;
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
public class UpdateFullStockUpdateTask {

    @Autowired
    UpdateStockListService updateAllStockMsg;

    @Autowired
    UpdateStockDailySaleService updateStockDailySaleService;

    @Autowired
    SysSettingMapper sysSettingMapper;

    @Autowired
    UpdateStockNetFlowService updateStockNetFlowService;

    ReentrantLock lock = new ReentrantLock();

    @Scheduled(fixedDelay = 1000*60*60)
    @Async(value="asyncExecutor")
    public void updateStockSaleData(){

        try{
            log.info("开始增量刷新全部Code历史交易信息,date:" + LocalDateTime.now());

            SysSetting setting = sysSettingMapper.selectByCode("updateStockSaleDataIsOn");
            if(null == setting || "0".equals(setting.getSysValue())) {
                log.info("配置未开启,结束增量刷新全部Code历史交易信息");
                return;
            }

            updateStockDailySaleService.updateAllStockDailySale();
            log.info("结束增量刷新全部Code历史交易信息,date:" + LocalDateTime.now());

        }catch (Exception e){
            log.error("updateStockSaleData 出现错误！",e);
        }

    }

    //每个6个小时更新1次
    @Scheduled(fixedDelay = 1000*60*60*6)
    @Async(value="asyncExecutor")
    public void updateAllStockMsg(){
        try{

            log.info("开始刷新全部Code基础信息,date:" + LocalDateTime.now());

            //为了避免出问题，只在后半夜执行
            LocalDateTime now = LocalDateTime.now();
            int hour = now.getHour();
            if(hour > 6){
                log.info("退出刷新全部Code基础信息，时间不符合要求,date:" + LocalDateTime.now());
                return;
            }
            SysSetting setting = sysSettingMapper.selectByCode("updateAllStockMsgIsOn");
            if(null == setting || "0".equals(setting.getSysValue())) {
                log.info("配置未开启,完成刷新全部Code基础信息");
                return;
            }
            this.getAllStock();
            log.info("完成刷新全部Code基础信息,date:" + LocalDateTime.now());
        }catch (Exception e){
            log.error("刷新全部Code基础信息出现错误！！！",e);
        }
    }

    @Scheduled(fixedDelay = 1000*60*30)
    @Async(value="asyncExecutor")
    public void updateStockMoneyFlowDate(){
        try{

            log.info("开始刷新南北资金流量信息,date:" + LocalDateTime.now());
            updateStockNetFlowService.updateFlowData();
            log.info("结束刷新南北资金流量信息,date:" + LocalDateTime.now());
        }catch (Exception e){
            log.error("updateStockSaleData 出现错误！",e);
        }
    }

    @Scheduled(cron = "0 30 14 * * ?")
    @Async(value="asyncExecutor")
    public void updateStockNowTradeData(){
        try{

            log.info("开始刷新全量实时交易信息,date:" + LocalDateTime.now());

            SysSetting setting = sysSettingMapper.selectByCode("updateRuntimeStockTradeData");
            if(null == setting || "0".equals(setting.getSysValue())) {
                log.info("配置未开启,退出刷新全量实时交易信息");
                return;
            }
            updateStockDailySaleService.renovateNowTradeDate();
            log.info("结束刷新全量实时交易信,date:" + LocalDateTime.now());
        }catch (Exception e){
            log.error("updateStockNowTradeData 出现错误！",e);
        }
    }

    private void getAllStock(){
        updateAllStockMsg.updateAllStockMsg();
    }

    public static void main(String[] args) {

    }

}
