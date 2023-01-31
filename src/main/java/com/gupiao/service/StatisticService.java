package com.gupiao.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gupiao.bean.api.StockCode;
import com.gupiao.enums.ApiUrlPath;
import com.gupiao.generator.domain.StockMarketData;
import com.gupiao.generator.domain.StockMarketXMovingAverage;
import com.gupiao.generator.domain.SysSetting;
import com.gupiao.generator.mapper.*;
import com.gupiao.service.thread.StockMarketDataThread;
import com.gupiao.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
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

    @Autowired
    StockDailyAverageDataMapper stockDailyAverageDataMapper;

    /**
     * 计算股票的xDailyPrice
     */
    public void computeXDaiyPrice(){

        //获取需要计算的天数信息
        SysSetting setting = sysSettingMapper.selectByCode("statistic_daily_list");
        List<String> days = Arrays.asList(setting.getSysValue().split(","));
        List<Integer> daysInt = new LinkedList<>();
        for (String d:days) {
            daysInt.add(Integer.valueOf(d));
        }

        //获取需要计算的股票信息


    }

/**以下代码不用**************************************************************************/
    public void cpmputeAllStackMovingAverage(){

        String res;
        try {
            //1.获取全部股票信息
            res = HttpService.getDataFromUrl(ApiUrlPath.STOCK_INFO_A_CODE_NAME, null);
            List<StockCode> r = new Gson().fromJson(res, new TypeToken<List<StockCode>>() {}.getType());

            for (StockCode code:r) {
                String endDate = DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE5);
                this.cpmputeXDayMovingAverage(code.getCode(),"2022-01-01",endDate);
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("StatisticService" + e.getMessage());
        }

    }


    /**
     * 计算一段时间的统计数据
     * @param code 股票编码
     * @param startDate 开始计算的时间
     * @param endDate 结束结算的时间
     */
    public void cpmputeXDayMovingAverage(String code,String startDate,String endDate){

        String tmpDate = startDate;
        //对 endDate 增加1天 用于判断结束
        String tmpEndDate = DateUtils.dateAddDays(endDate,DateUtils.DATE_FORMATE5,1L);

        while(!tmpDate.equals(tmpEndDate)){

            log.info("tmpDate=" + tmpDate + ";tmpEndDate=" + tmpEndDate);

            //查询历史交易数据，比如计算日期是2022-09-01，需要查询日期小于2022-09-01的150条交易记录
            List<StockMarketData> res = stockMarketDataMapper.selectByCodeAndDate(code,tmpDate,150);

            //查询当天记录是否已经存在，如果已经存在则不用计算
            StockMarketXMovingAverage resDate = stockMarketXMovingAverageMapper.selectByCodeAndDate(code,tmpDate);

            if( null != resDate ){
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

        // endDate 是记录时间，依赖的历史记录截止时间是 tmpEndDate = endDate-1
        String tmpEndDate = DateUtils.dateAddDays(endDate,DateUtils.DATE_FORMATE5,-1L);

        StockMarketXMovingAverage stockMarketXMovingAverage = new StockMarketXMovingAverage();
        BigDecimal x3,x5,x7,x10,x15,x30,x60,x90,x120,x150;

        //查询计算日期的交易数据，主要是获取当日的收盘价
        StockMarketData stockMarketData = stockMarketDataMapper.selectByCode(code,endDate);

        if(null == stockMarketData){
            throw new RuntimeException("code" + code + " 在" + endDate + "日不存在数据，不能获取当日收盘价" );
        }

        try {
            x3 = this.getXDayMovingAverage(res,code,tmpEndDate,3);
            log.info("x3=" + x3);
        }catch (Exception e){
            log.info(e.getMessage());
            x3 = BigDecimal.ZERO;
        }

        try {
            x5 = this.getXDayMovingAverage(res,code,tmpEndDate,5);
            log.info("x5=" + x5);
        }catch (Exception e){
            log.info(e.getMessage());
            x5 = BigDecimal.ZERO;
        }

        try {
            x7 = this.getXDayMovingAverage(res,code,tmpEndDate,7);
        }catch (Exception e){
            log.info(e.getMessage());
            x7 = BigDecimal.ZERO;
        }

        try {
            x10 = this.getXDayMovingAverage(res,code,tmpEndDate,10);
        }catch (Exception e){
            log.info(e.getMessage());
            x10 = BigDecimal.ZERO;
        }

        try {
            x15 = this.getXDayMovingAverage(res,code,tmpEndDate,15);
        }catch (Exception e){
            log.info(e.getMessage());
            x15 = BigDecimal.ZERO;
        }

        try {
            x30 = this.getXDayMovingAverage(res,code,tmpEndDate,30);
        }catch (Exception e){
            log.info(e.getMessage());
            x30 = BigDecimal.ZERO;
        }

        try {
            x60 = this.getXDayMovingAverage(res,code,tmpEndDate,60);
        }catch (Exception e){
            x60 = BigDecimal.ZERO;
        }

        try {
            x90 = this.getXDayMovingAverage(res,code,tmpEndDate,90);
        }catch (Exception e){
            log.debug(e.getMessage());
            x90 = BigDecimal.ZERO;
        }

        try {
            x120 = this.getXDayMovingAverage(res,code,tmpEndDate,120);
        }catch (Exception e){
            log.debug(e.getMessage());
            x120 = BigDecimal.ZERO;
        }

        try {
            x150 = this.getXDayMovingAverage(res,code,tmpEndDate,150);
        }catch (Exception e){
            log.debug(e.getMessage());
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
        log.info("code="+code + ",endDate="+endDate+"days=" + days);
        if(null == res || res.size() < days){
            throw new RuntimeException("code:" + code + " 历史数据不足，不能计算过去" + days + "天的平均数据！");
        }
        BigDecimal all = BigDecimal.valueOf(0.0);
        Integer size = 0;

        for (StockMarketData d:res) {

            if(size >= days){
                break;
            }
            all = all.add(d.getClosingPrice());
            log.info("days="+days + "value=" + all);
            size++;

        }
        all = all.divide(BigDecimal.valueOf(days),8, BigDecimal.ROUND_HALF_UP);
        return all;

    }

}
