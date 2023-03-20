package com.gupiao.service;

import com.gupiao.generator.domain.StockDetail;
import com.gupiao.generator.domain.StockMarketData;
import com.gupiao.generator.domain.StockMarketRuntimeData;
import com.gupiao.generator.mapper.StockDetailMapper;
import com.gupiao.generator.mapper.StockMarketDataMapper;
import com.gupiao.generator.mapper.StockMarketRuntimeDataMapper;
import com.gupiao.util.DateUtils;
import com.gupiao.util.DingUtil;
import com.gupiao.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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

        this.getDingDingMsg(res);

        this.sendUpAndDown();

        this.sendDownStock();

    }

    /**
     * 为避免消息过大，5个一组发送
     * @param res
     * @return
     */
    public String getDingDingMsg( List<StockMarketData> res ){

        //4.补充发送信息
        if( null == res || res.size() < 1 ){
            DingUtil.sendDingTalk( "推断信息生成失败！！！" );
            return "";
        }

        String nowDate = DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE5);
        int i = 0;
        int groupId = 1;
        String msg = "第" + groupId + "组 \n";

        //组装信息
        for (StockMarketData marketData:res) {

            StockDetail stockDetail = stockDetailMapper.selectByCode( marketData.getStockCode() );
            if( null == stockDetail){
                continue;
            }

            StockMarketRuntimeData runtimeData = stockMarketRuntimeDataMapper.selectByDateAndCode(nowDate,marketData.getStockCode());
            if(null != runtimeData){
                if(runtimeData.getNewPrice().compareTo(BigDecimal.valueOf(30)) > 0){
                    continue;
                }
            }
            msg += "code:" + marketData.getStockCode() + "\n";
            msg += "名称:" + stockDetail.getStockName() + "\n";
            msg += "实时价格:" + (null == runtimeData ? "----" : runtimeData.getNewPrice()) + "\n";
            msg += "波动幅度:" + (null == runtimeData ? "----" : runtimeData.getUpsAndDowns()) + "\n";
            msg += "换手率:" + (null == runtimeData ? "----" : runtimeData.getTurnoverRate()) + "\n";
            msg += "url:" + Util.getUrlByCode( marketData.getStockCode() ) + "\n";
            msg += "---------------------------\n";
            i++;

            //每组5个，太多了会导致解析失败
            if(i >= 5){
                groupId++;
                i=0;
                DingUtil.sendDingTalk(msg);
                msg = "第" + groupId + "组 \n";
                //避免钉钉消息乱序，此处睡眠一下
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
            }

            //发送10组就退出
            if(groupId > 10){
                break;
            }

        }

        return msg;

    }

    public void sendUpAndDown(){

        String nowDate = DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE5);
        List<String> createTimeList= stockMarketRuntimeDataMapper.getCreateTimeByDate(nowDate);
        if(null == createTimeList || createTimeList.size() == 0){
            return;
        }

        StockMarketRuntimeData stockMarketRuntimeData = stockMarketRuntimeDataMapper.selectByP(nowDate,createTimeList.get(0));

        if(null == stockMarketRuntimeData){
            DingUtil.sendDingTalk("当天涨跌统计结果均为0");
        }else{
            DingUtil.sendDingTalk("今天上涨个数:" + stockMarketRuntimeData.getUpCount() +
                    "\n今天下跌个数为:" + stockMarketRuntimeData.getDownCount());
        }

    }

    public void sendDownStock(){

        String nowDate = DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE5);
        List<String> createTimeList= stockMarketRuntimeDataMapper.getCreateTimeByDate(nowDate);
        if(null == createTimeList || createTimeList.size() == 0){
            return;
        }
        List<StockMarketRuntimeData> res = stockMarketRuntimeDataMapper.selectDownStock(nowDate,createTimeList.get(0),30);

        if(null == res){
            return;
        }

        int groupId = 1, i = 0;

        String msg = "下跌最严重stock[ "+groupId+"]\n";

        for (StockMarketRuntimeData runtimeData:res) {

            msg += "code:" + runtimeData.getStockCode() + "\n";
            msg += "名称:" + runtimeData.getStockName() + "\n";
            msg += "实时价格:" + runtimeData.getNewPrice() + "\n";
            msg += "波动幅度:" + runtimeData.getUpsAndDowns() + "\n";
            msg += "百分比:" + runtimeData.getUpsAndDowns() + "\n";
            msg += "换手率:" + runtimeData.getTurnoverRate() + "\n";
            msg += "url:" + Util.getUrlByCode( runtimeData.getStockCode() ) + "\n";
            msg += "---------------------------\n";
            i++;

            if(i >= 5){
                groupId++;
                i=0;
                DingUtil.sendDingTalk(msg);
                msg = "下跌最严重stock[ "+groupId+"]\n";
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
            }

        }
    }




}
