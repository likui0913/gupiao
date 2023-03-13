package com.gupiao.service;

import com.gupiao.generator.domain.StockDetail;
import com.gupiao.generator.domain.StockMarketData;
import com.gupiao.generator.mapper.StockDetailMapper;
import com.gupiao.generator.mapper.StockMarketDataMapper;
import com.gupiao.generator.mapper.StockMarketRuntimeDataMapper;
import com.gupiao.util.DateUtils;
import com.gupiao.util.DingUtil;
import com.gupiao.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * 预测服务类
 */
@Component
@Slf4j
public class CalculateService {

    @Autowired
    StockMarketRuntimeDataMapper stockMarketRuntimeDataMapper;

    @Autowired
    StockMarketDataMapper stockMarketDataMapper;

    @Autowired
    StockDetailMapper stockDetailMapper;

    /**
     * 预测明天股票信息
     * //
     */
    public void calculateTomorrowStock(){

        //1.计算当前时间
        String startDate,endDate,nowDate;
        nowDate = DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE5);
        DayOfWeek week = LocalDate.now().getDayOfWeek();
        if(DayOfWeek.SATURDAY.equals(week) || DayOfWeek.SUNDAY.equals(week)){
            //周六或者周日不做处理
            return;
        }
        if(DayOfWeek.MONDAY.equals(week)){
            //如果是周一，最后有数据的日期是上周五
            endDate = DateUtils.dateAddDays(nowDate,DateUtils.DATE_FORMATE5, -3L);

        }else{
            endDate = DateUtils.dateAddDays(nowDate,DateUtils.DATE_FORMATE5, -1L);
        }
        startDate = DateUtils.dateAddDays(endDate,DateUtils.DATE_FORMATE5, -10L);

        //2.检查数据是否具备
        Integer runTimeDataCount = stockMarketRuntimeDataMapper.selectCountByDate(nowDate);

        //3.生成推断结果
        List<StockMarketData> res = stockMarketDataMapper.selectCalculateResultByDate(startDate,endDate);

        //4.补充发送信息
        if( null == res || res.size() < 1 ){
            DingUtil.sendDingTalk( "推断信息生成失败！！！" );
            return;
        }

        this.getDingDingMsg(res);
    }

    /**
     * 为避免消息过大，5个一组发送
     * @param res
     * @return
     */
    public String getDingDingMsg( List<StockMarketData> res ){

        String msg = "[GP]通知: \n";
        int i = 0;
        //组装信息
        for (StockMarketData marketData:res) {

            StockDetail stockDetail = stockDetailMapper.selectByCode( marketData.getStockCode() );
            if( null == stockDetail){
                continue;
            }
            msg += "code:" + marketData.getStockCode() + "\n";
            msg += "name:" + stockDetail.getStockName() + "\n";
            msg += "url:" + Util.getUrlByCode( marketData.getStockCode() ) + "\n";
            msg += "-----------------------------------\n";
            i++;

            if(i >= 5){
                log.info("dingdingmsg:{}",msg);
                DingUtil.sendDingTalk(msg);
                i=0;
                msg = "[GP]通知: \n";
            }

        }

        return msg;

    }

}
