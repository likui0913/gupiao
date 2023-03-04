package com.gupiao.service;

import com.gupiao.generator.domain.StockMarketStaticData;
import com.gupiao.generator.domain.SysSetting;
import com.gupiao.generator.mapper.StockMarketStaticDataMapper;
import com.gupiao.generator.mapper.SysSettingMapper;
import com.gupiao.util.DateUtils;
import com.gupiao.util.DingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class SendDingDingMsgService {

    @Autowired
    StatisticService statisticService;

    @Autowired
    SysSettingMapper sysSettingMapper;

    @Autowired
    StockMarketStaticDataMapper stockMarketStaticDataMapper;

    /**
     * 统计 过去N天持续下跌的code
     * @param days 间隔天数
     * @param startPrice
     * @param endPrice
     * @param limited
     */
    public void sendLastNDaysLowPriceCode(Integer days,Integer startPrice,Integer endPrice,Integer limited){

        //读取上一次发送消息的时间,里面是最后一次发送的时间，如果是昨天，则直接退出
        SysSetting setting = sysSettingMapper.selectByCode("sendDingDingLastDaysMsg");
        String lastSendDate = DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE5);
        lastSendDate = DateUtils.dateAddDays(lastSendDate,DateUtils.DATE_FORMATE5, -1L);
        if( null == setting){
            setting = new SysSetting();
            setting.setSysKey("sendDingDingLastDaysMsg");
            setting.setSysValue("2023-01-01");
            setting.setDes("最后一次发送统计信息的日期");
            sysSettingMapper.insert( setting );
        }

        if(lastSendDate.equals(setting.getSysValue())){
            return;//昨天数据已发送，直接退出
        }

        List<StockMarketStaticData> res = stockMarketStaticDataMapper.selectByParameter(7, lastSendDate, 0,
                25,20,null,null,null);

        //没有查询结果，直接返回
        if(CollectionUtils.isEmpty(res)){
            return;
        }
        String msg = "[GP]通知: 时间:" + lastSendDate + "\n";
        for (StockMarketStaticData d:res){
            msg += "**************************\n" +
                    "code:" + d.getStockCode() + "\n" +
                    "downdays:" + d.getDownDays() + "\n" +
                    "date:" + d.getTradeDate() + "\n" +
                    "avg:" + d.getTurnoverRateAvg().toString() + "\n";
        }
        DingUtil.sendDingTalk(msg);
        sysSettingMapper.updateByKey("sendDingDingLastDaysMsg",lastSendDate);

    }
}
