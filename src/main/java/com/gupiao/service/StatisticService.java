package com.gupiao.service;

import com.gupiao.generator.domain.StockMarketData;
import com.gupiao.generator.domain.StockMarketXMovingAverage;
import com.gupiao.generator.mapper.*;
import com.gupiao.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class StatisticService {

    @Autowired
    StockDetailMapper stockDetailMapper;

    @Autowired
    IndustryTransactionsMapper industryTransactionsMapper;

    @Autowired
    StockMarketDataMapper stockMarketDataMapper;

    @Autowired
    SysSettingMapper sysSettingMapper;

    @Autowired
    StockMarketXMovingAverageMapper stockMarketXMovingAverageMapper;

    /**
     * 计算一段时间的统计数据
     * @param code
     * @param startDate
     * @param endDate
     */
    public void cpmputeXDayMovingAverage(String code,String startDate,String endDate){
        String tmpDate = startDate;
        String tmpEndDate = DateUtils.dateAddDays(endDate,DateUtils.DATE_FORMATE5,1L);
        List<StockMarketData> res = stockMarketDataMapper.selectByCodeAndDate(code,endDate,150);

        while(!tmpDate.equals(tmpEndDate)){
            log.info("tmpDate=" + tmpDate + ";tmpEndDate=" + tmpEndDate);

            StockMarketXMovingAverage resDate = stockMarketXMovingAverageMapper.selectByCodeAndDate(code,tmpDate);
            if(null != resDate){
                log.info("code=" + code + " 在日期=" + tmpDate + "的数据已经存在");
                tmpDate = DateUtils.dateAddDays(tmpDate,DateUtils.DATE_FORMATE5,1L);
                continue;
            }
            try{
                StockMarketXMovingAverage stockMarketXMovingAverage = this.computeAllDayMovingAverage(res,code,tmpDate);
                stockMarketXMovingAverageMapper.insert(stockMarketXMovingAverage);
            }catch (Exception e) {
                log.error("code" + code + "统计数据，计算错误:" + e.getMessage());
            }
            tmpDate = DateUtils.dateAddDays(tmpDate,DateUtils.DATE_FORMATE5,1L);
        }

    }

    public StockMarketXMovingAverage computeAllDayMovingAverage(
            List<StockMarketData> res,
            String code,
            String endDate){

        StockMarketXMovingAverage stockMarketXMovingAverage = new StockMarketXMovingAverage();
        BigDecimal x3,x5,x7,x10,x15,x30,x60,x90,x120,x150;

        String tmpEndDate = DateUtils.dateAddDays(endDate,DateUtils.DATE_FORMATE5,1L);
        StockMarketData stockMarketData = stockMarketDataMapper.selectByCode(code,tmpEndDate);
        if(null == stockMarketData){
            throw new RuntimeException("code" + code + " 在" + tmpEndDate + "日不存在数据，不能生成统计数据" );
        }

        try {
            x3 = this.getXDayMovingAverage(res,code,endDate,3);
        }catch (Exception e){
            x3 = BigDecimal.ZERO;
        }

        try {
            x5 = this.getXDayMovingAverage(res,code,endDate,3);
        }catch (Exception e){
            x5 = BigDecimal.ZERO;
        }

        try {
            x7 = this.getXDayMovingAverage(res,code,endDate,3);
        }catch (Exception e){
            x7 = BigDecimal.ZERO;
        }

        try {
            x10 = this.getXDayMovingAverage(res,code,endDate,3);
        }catch (Exception e){
            x10 = BigDecimal.ZERO;
        }

        try {
            x15 = this.getXDayMovingAverage(res,code,endDate,3);
        }catch (Exception e){
            x15 = BigDecimal.ZERO;
        }

        try {
            x30 = this.getXDayMovingAverage(res,code,endDate,3);
        }catch (Exception e){
            x30 = BigDecimal.ZERO;
        }

        try {
            x60 = this.getXDayMovingAverage(res,code,endDate,3);
        }catch (Exception e){
            x60 = BigDecimal.ZERO;
        }

        try {
            x90 = this.getXDayMovingAverage(res,code,endDate,3);
        }catch (Exception e){
            x90 = BigDecimal.ZERO;
        }

        try {
            x120 = this.getXDayMovingAverage(res,code,endDate,3);
        }catch (Exception e){
            x120 = BigDecimal.ZERO;
        }

        try {
            x150 = this.getXDayMovingAverage(res,code,endDate,3);
        }catch (Exception e){
            x150 = BigDecimal.ZERO;
        }

        stockMarketXMovingAverage.setStockCode(code);
        stockMarketXMovingAverage.setTradeDate(endDate);
        stockMarketXMovingAverage.setX3(x3);
        stockMarketXMovingAverage.setX5(x5);
        stockMarketXMovingAverage.setX7(x7);
        stockMarketXMovingAverage.setX10(x10);
        stockMarketXMovingAverage.setX15(x15);
        stockMarketXMovingAverage.setX30(x30);
        stockMarketXMovingAverage.setX60(x60);
        stockMarketXMovingAverage.setX90(x90);
        stockMarketXMovingAverage.setX120(x120);
        stockMarketXMovingAverage.setX150(x150);
        stockMarketXMovingAverage.setClosingPrice(stockMarketData.getClosingPrice());

        return stockMarketXMovingAverage;

    }


    /**
     * 获取X日均线计算结果
     * @param code
     * @param days
     */
    public BigDecimal getXDayMovingAverage(List<StockMarketData> res,String code,String endDate,Integer days){

        if(null == res || res.size() < days){
            throw new RuntimeException("code:" + code + " 历史数据不足，不能计算过去" + days + "天的平均数据！");
        }
        if(!res.get(0).getTradeDate().equals(endDate)){
            throw new RuntimeException("code:" + code + " 的最新数据是"+ res.get(0).getTradeDate() +"，不能计算 " + endDate + "的平均数据！");
        }
        BigDecimal all = BigDecimal.valueOf(0.0);
        Integer size = 0;
        for (StockMarketData d:res) {
            all = all.add(d.getClosingPrice());
            if(size > days){
                break;
            }
            size++;
        }
        all = all.divide(BigDecimal.valueOf(days),8, BigDecimal.ROUND_HALF_UP);
        return all;

    }

}
