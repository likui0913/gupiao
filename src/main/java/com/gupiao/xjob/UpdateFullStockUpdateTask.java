package com.gupiao.xjob;

import com.gupiao.generator.domain.SysSetting;
import com.gupiao.generator.mapper.SysSettingMapper;
import com.gupiao.service.*;
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

    @Autowired
    CalculateService calculateService;

    ReentrantLock lock = new ReentrantLock();

    /**
     * 刷新股票的交易信息
     */
    @Scheduled(fixedDelay = 1000*60*60)
    @Scheduled(cron = "0 10 15 * * ?")
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

    /**
     * 更新全量股票元数据信息
     */
    @Scheduled(cron = "0 30 9 * * ?")
    @Async(value="asyncExecutor")
    public void updateAllStockMsg(){

        try{

            log.info("开始刷新全部Code基础信息,date:" + LocalDateTime.now());

            //为了避免出问题，只在后半夜执行
            LocalDateTime now = LocalDateTime.now();
            int hour = now.getHour();
            if(hour > 6 && 14 > hour){
                log.info("退出刷新全部Code基础信息，时间不符合要求,要求小于6点-或者大于15点,date:" + LocalDateTime.now());
                return;
            }
            SysSetting setting = sysSettingMapper.selectByCode("updateAllStockMsgIsOn");
            if(null == setting || "0".equals(setting.getSysValue())) {
                log.info("配置未开启,完成刷新全部Code基础信息");
                return;
            }
            updateAllStockMsg.updateAllStockMsg();
            log.info("完成刷新全部Code基础信息,date:" + LocalDateTime.now());

        }catch (Exception e){
            log.error("刷新全部Code基础信息出现错误！！！",e);
        }

    }

    /**
     * 更新南北向资金流信息
     */
    @Scheduled(cron = "0 0 16 * * ?")
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

    /**
     * 更新每天的实时交易数据
     */
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

            //1.刷新当前实时交易信息
            updateStockDailySaleService.renovateNowTradeDate();

            //2.计算推算出的股票信息
            calculateService.calculateTomorrowStock();

            log.info("结束刷新全量实时交易信,date:" + LocalDateTime.now());

        }catch (Exception e){
            log.error("updateStockNowTradeData 出现错误！",e);
        }

    }

    @Scheduled(cron = "0 45 9 * * ?")
    @Async(value="asyncExecutor")
    public void updateStockNowTradeData2(){

        try{

            log.info("开始刷新全量实时交易信息,date:" + LocalDateTime.now());
            SysSetting setting = sysSettingMapper.selectByCode("updateRuntimeStockTradeData");
            if(null == setting || "0".equals(setting.getSysValue())) {
                log.info("配置未开启,退出刷新全量实时交易信息");
                return;
            }

            //1.刷新当前实时交易信息
            updateStockDailySaleService.renovateNowTradeDate();

            log.info("结束刷新全量实时交易信,date:" + LocalDateTime.now());

        }catch (Exception e){
            log.error("updateStockNowTradeData 出现错误！",e);
        }

    }

    @Scheduled(cron = "0 0 10 * * ?")
    @Async(value="asyncExecutor")
    public void updateStockNowTradeData3(){

        try{

            log.info("开始刷新全量实时交易信息,date:" + LocalDateTime.now());
            SysSetting setting = sysSettingMapper.selectByCode("updateRuntimeStockTradeData");
            if(null == setting || "0".equals(setting.getSysValue())) {
                log.info("配置未开启,退出刷新全量实时交易信息");
                return;
            }

            //1.刷新当前实时交易信息
            updateStockDailySaleService.renovateNowTradeDate();

            log.info("结束刷新全量实时交易信,date:" + LocalDateTime.now());

        }catch (Exception e){
            log.error("updateStockNowTradeData 出现错误！",e);
        }

    }


}
