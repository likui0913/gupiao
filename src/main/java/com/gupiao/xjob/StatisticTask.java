package com.gupiao.xjob;

import com.gupiao.generator.domain.SysSetting;
import com.gupiao.generator.mapper.SysSettingMapper;
import com.gupiao.service.StatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 处理统计任务
 */
@Configuration
@EnableScheduling
@Slf4j
public class StatisticTask {

    @Autowired
    StatisticService statisticService;

    @Autowired
    SysSettingMapper sysSettingMapper;

    /**
     * 计算每天固定时间间隔的价格差值
     */
    @Scheduled(fixedDelay = 1000*60*60)
    public void updateStockSaleData(){
        try{
            log.info("开始统计历史交易信息,date:" + LocalDateTime.now());

            SysSetting setting = sysSettingMapper.selectByCode("staticStockSaleDataIsOn");
            if(null == setting || "0".equals(setting.getSysValue())) {
                log.info("配置未开启,结束统计历史交易信息");
                return;
            }

            SysSetting settingIntervalDay = sysSettingMapper.selectByCode("staticStockSaleIntervalDay");

            if(null == settingIntervalDay || "".equals(settingIntervalDay.getSysValue())){
                log.info("staticStockSaleIntervalDay配置错误,结束统计历史交易信息");
                return;
            }
            List<String> days = Arrays.asList(settingIntervalDay.getSysValue().split(","));
            List<Integer> daysInt = new LinkedList<>();
            for (String d:days) {
                daysInt.add(Integer.valueOf(d));
            }

            statisticService.computeNDayStaticData(daysInt);

            log.info("结束统计历史交易信息,date:" + LocalDateTime.now());
        }catch (Exception e){
            log.error("StatisticTask 出现错误！",e);
        }
    }

    /**
     * 计算每天两个固定时间间隔的价格差值，第一个时间差值是N天，第二个是M天，用M/N观察股票的上涨和下跌走势
     */
    @Scheduled(fixedDelay = 1000*60)
    public void computeTowDayDiffData(){
        try{

            log.info("开始统计两段时间差值交易信息,date:" + LocalDateTime.now());

            SysSetting setting = sysSettingMapper.selectByCode("staticTowDaysSaleDataIsOn");

            if(null == setting || "0".equals(setting.getSysValue())) {
                log.info("配置未开启,结束统计历史交易信息");
                return;
            }

            SysSetting staticTowDaysSetting = sysSettingMapper.selectByCode("staticTowDaysSetting");

            if(null == staticTowDaysSetting || "".equals(staticTowDaysSetting.getSysValue())){
                log.info("staticTowDaysSetting配置错误,结束统计历史交易信息");
                return;
            }
            List<String> days = Arrays.asList(staticTowDaysSetting.getSysValue().split(";"));

            statisticService.computeTwoDayDiffPriceData(days);

            log.info("结束统计两段时间差值交易信息,date:" + LocalDateTime.now());

        }catch (Exception e){
            log.error("StatisticTask 出现错误！",e);
        }
    }

}
