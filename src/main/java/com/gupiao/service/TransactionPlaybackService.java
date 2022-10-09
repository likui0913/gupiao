package com.gupiao.service;

import com.gupiao.bean.ComputeDailyBean;
import com.gupiao.bean.api.StockCode;
import com.gupiao.generator.domain.StockMarketData;
import com.gupiao.generator.mapper.StockMarketDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 交易回放计算
 */
@Component
@Slf4j
public class TransactionPlaybackService {

    @Autowired
    StockMarketDataMapper stockMarketDataMapper;

    /**
     * 模拟股票在指定时间段内的交易情况
     * @param code
     * @param startDate
     * @param endDate
     * @return
     */
    public List<ComputeDailyBean> computeTransactionPlayback(String code, String startDate, String endDate){

        List<ComputeDailyBean> res = new LinkedList<>();

        //1.获取股票信息
        List<StockMarketData> stockMarketDataList = stockMarketDataMapper.selectByCodeAndTwoDate(code,startDate,endDate);
        if( null == stockMarketDataList || stockMarketDataList.size() < 1){
            return null;
        }

        BigDecimal baseMount = BigDecimal.valueOf(10000.0);
        //2.计算每天的计算结果
        for (StockMarketData stockMarketData:stockMarketDataList) {
            baseMount = baseMount.multiply(BigDecimal.valueOf(1).add(stockMarketData.getQuoteChange().divide(BigDecimal.valueOf(100))));
            ComputeDailyBean b = ComputeDailyBean.createBean(stockMarketData.getTradeDate(),stockMarketData.getQuoteChange(),baseMount);
            res.add(b);
        }

        return res;
    }

    /**
     * 模拟股票在指定时间段内的交易情况
     * @param code
     * @param startDate
     * @param days
     * @return
     */
    public List<ComputeDailyBean> computeTransactionPlaybackDays(String code, String startDate, String days){

        List<ComputeDailyBean> res = new LinkedList<>();

        //1.获取股票信息
        List<StockMarketData> stockMarketDataList = stockMarketDataMapper.selectByStartDateAndDays(code,startDate,Integer.valueOf(days));
        if( null == stockMarketDataList || stockMarketDataList.size() < 1){
            return null;
        }

        BigDecimal baseMount = BigDecimal.valueOf(10000.0);
        //2.计算每天的计算结果
        for (StockMarketData stockMarketData:stockMarketDataList) {
            baseMount = baseMount.multiply(BigDecimal.valueOf(1).add(stockMarketData.getQuoteChange().divide(BigDecimal.valueOf(100))));
            ComputeDailyBean b = ComputeDailyBean.createBean(stockMarketData.getTradeDate(),stockMarketData.getQuoteChange(),baseMount);
            res.add(b);
        }

        return res;
    }

}
