package com.gupiao.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gupiao.bean.api.StockCode;
import com.gupiao.enums.ApiUrlPath;
import com.gupiao.enums.LogSwitchEnums;
import com.gupiao.generator.domain.*;
import com.gupiao.generator.mapper.*;
import com.gupiao.service.thread.StockMarketDataThread;
import com.gupiao.util.DateUtils;
import com.gupiao.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

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

    @Autowired
    StockMarketStaticDataMapper stockMarketStaticDataMapper;

    @Autowired
    StockMarketTowDayDiffDataMapper stockMarketTowDayDiffDataMapper;

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

    /**
     * 计算表：的统计信息
     * @param days
     */
    public void computeNDayStaticData(List<Integer> days){

        Long startTime = System.currentTimeMillis();
        //1.获取全量股票信息，获取当前时间
        List<StockDetail> localAllStock = getLocalAllStock();
        //2.获取当前时间
        String endDateNow = DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE5);
        String endDateT_1 = DateUtils.dateAddDays(endDateNow,DateUtils.DATE_FORMATE5, -1L);

        Integer computeCount = 0,errorCount = 0;
        for (StockDetail sd:localAllStock) {

            if(sd.getStockType().equals(1)){
                continue;
            }
            try {
                if(LogUtil.getLogSwitchByKey(LogSwitchEnums.STOCK_STATIC.getName(), Boolean.FALSE)){
                    log.info("computeNDayStaticData 处理code:" + sd.getStockCode());
                }
                this.computeByCodeAndDateAndDays(sd,endDateT_1,days);
                computeCount++;
            }catch (Exception e){
                log.error("computeNDayStaticData 处理出错，code=" + sd.getStockCode(),e);
                errorCount++;
            }

        }

        log.info("computeNDayStaticData 处理完成，computeCount=" + computeCount + ",errorCount=" + errorCount);
        log.info("computeNDayStaticData 处理完成，时间花费：" + (System.currentTimeMillis() - startTime));

    }

    /**
     * 计算两个时间段内价格差异服务--未完成
     * @param days
     */
    public void computeTwoDayDiffPriceData(List<String> days){

        Long startTime = System.currentTimeMillis();
        //1.获取全量股票信息，获取当前时间
        List<StockDetail> localAllStock = getLocalAllStock();
        //2.获取当前时间
        String endDateNow = DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE5);
        String endDateT_1 = DateUtils.dateAddDays(endDateNow,DateUtils.DATE_FORMATE5, -1L);

        Integer computeCount = 0,errorCount = 0;
        for (StockDetail sd:localAllStock) {

            if(sd.getStockType().equals(1)){
                continue;
            }
            try {
                if(LogUtil.getLogSwitchByKey(LogSwitchEnums.STOCK_STATIC_TWO_DAY.getName(), Boolean.FALSE)){
                    log.info("computeTwoDayDiffPriceData 处理code:" + sd.getStockCode());
                }
                this.computeTwoDayDiffByCodeAndDateAndDays(sd,endDateT_1,days);
                computeCount++;
            }catch (Exception e){
                log.error("computeTwoDayDiffPriceData 处理出错，code=" + sd.getStockCode(),e);
                errorCount++;
            }

        }

        log.info("computeTwoDayDiffPriceData 处理完成，computeCount=" + computeCount + ",errorCount=" + errorCount);
        log.info("computeTwoDayDiffPriceData 处理完成，时间花费：" + (System.currentTimeMillis() - startTime));

    }

    /**
     * @param sd 需要计算的code
     * @param endDate 截止时间，包含此日期
     * @param days 要计算的间隔天数集合
     */
    public void computeByCodeAndDateAndDays(StockDetail sd, String endDate, List<Integer> days){

        //1.获取已经计算好的最新的时间，如果没有，默认倒序60天
        StockMarketStaticData staticData = stockMarketStaticDataMapper.selectMaxDate(sd.getStockCode());
        String queryEndDate = "",queryStartDate = "";
        if(null == staticData){
            // 倒序最大60天
            // 因为要计算第60天数据，所以要多查询一部分数据，默认多查询30天，所以要查询共计90天数据
            queryEndDate = DateUtils.dateAddDays(DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE5),DateUtils.DATE_FORMATE5, -60L);
        }else{
            queryEndDate = staticData.getTradeDate();
        }

        if(queryEndDate.equals(endDate)){
            //昨天的数据已经计算了，退出
            return;
        }

        //计算N+1的数据,queryEndDate存放需要计算的日期
        queryEndDate = DateUtils.dateAddDays(queryEndDate, DateUtils.DATE_FORMATE5,1L);
        while(true){

            StockMarketData d = stockMarketDataMapper.selectByCode(sd.getStockCode(),queryEndDate);
            if(d != null){
                break;
            }

            if(queryEndDate.equals(endDate)){
                return;
            }
            queryEndDate = DateUtils.dateAddDays(queryEndDate, DateUtils.DATE_FORMATE5,1L);

        }

        //查询算需要的最早天数数据,因为计算需要，所以要多查询一部分数据，最多查询30天数据即可
        queryStartDate = DateUtils.dateAddDays(queryEndDate,DateUtils.DATE_FORMATE5, -30L);
        if(LogUtil.getLogSwitchByKey(LogSwitchEnums.STOCK_STATIC.getName(), Boolean.FALSE)){
            log.info("computeByCodeAndDateAndDays code:" + sd.getStockCode() + ",queryStartDate:" + queryStartDate + ",queryEndDate:" + queryEndDate);
        }
        List<StockMarketData> res = stockMarketDataMapper.selectByCodeAndTwoDateDesc(sd.getStockCode(),queryStartDate,queryEndDate);
        if(res == null || res.size() == 0){
            //不存在对应数据，直接退出
            return;
        }
        res = sortByDate(res);

        //循环计算对应结果，写如数据库
        for (Integer day:days){
            if(res.size() <= day){
                //历史数据不能满足计算天数要求，直接退出
                continue;
            }
            StockMarketStaticData data = compute(sd,queryStartDate,queryEndDate,day,res);
            insert(data);
        }

    }


    /**
     * @param sd 需要计算的code
     * @param endDate 截止时间，包含此日期
     * @param days 要计算的间隔天数集合
     */
    public void computeTwoDayDiffByCodeAndDateAndDays(StockDetail sd, String endDate, List<String> days){

        //1.获取已经计算好的最新的时间，如果没有，默认倒序60天
        StockMarketTowDayDiffData staticData = stockMarketTowDayDiffDataMapper.selectMaxDate(sd.getStockCode());
        String queryEndDate = "",queryStartDate = "";
        if(null == staticData){
            // 倒序最大60天
            // 因为要计算第60天数据，所以要多查询一部分数据，默认多查询30天，所以要查询共计90天数据
            queryEndDate = DateUtils.dateAddDays(DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE5),DateUtils.DATE_FORMATE5, -60L);
        }else{
            queryEndDate = staticData.getTradeDate();
        }

        if(queryEndDate.equals(endDate)){
            //昨天的数据已经计算了，退出
            return;
        }

        //计算N+1的数据,queryEndDate存放需要计算的日期
        queryEndDate = DateUtils.dateAddDays(queryEndDate, DateUtils.DATE_FORMATE5,1L);
        while(true){

            StockMarketData d = stockMarketDataMapper.selectByCode(sd.getStockCode(),queryEndDate);
            if(d != null){
                break;
            }

            if(queryEndDate.equals(endDate)){
                return;
            }
            queryEndDate = DateUtils.dateAddDays(queryEndDate, DateUtils.DATE_FORMATE5,1L);

        }

        //查询算需要的最早天数数据,因为计算需要，所以要多查询一部分数据，最多查询60天数据即可
        queryStartDate = DateUtils.dateAddDays(queryEndDate,DateUtils.DATE_FORMATE5, -60L);
        if(LogUtil.getLogSwitchByKey(LogSwitchEnums.STOCK_STATIC_TWO_DAY.getName(), Boolean.FALSE)){
            log.info("computeTwoDayDiffByCodeAndDateAndDays code:" + sd.getStockCode() + ",queryStartDate:" + queryStartDate + ",queryEndDate:" + queryEndDate);
        }
        List<StockMarketData> res = stockMarketDataMapper.selectByCodeAndTwoDateDesc(sd.getStockCode(),queryStartDate,queryEndDate);
        if(res == null || res.size() == 0){
            //不存在对应数据，直接退出
            return;
        }
        res = sortByDate(res);

        //循环计算对应结果，写如数据库
        for (String day:days){

            String[] oneDays = day.split("-");
            Integer dayx = Integer.valueOf(oneDays[0]);
            Integer dayy = Integer.valueOf(oneDays[1]);

            if( res.size() <= (dayx + dayy + 1) ){
                //历史数据不能满足计算天数要求，直接退出
                if(LogUtil.getLogSwitchByKey(LogSwitchEnums.STOCK_STATIC_TWO_DAY.getName(), Boolean.FALSE)){
                    log.info("computeTwoDayDiffByCodeAndDateAndDays 历史数据不能满足计算天数要求 code:" + sd.getStockCode()
                            + ",queryStartDate:" + queryStartDate
                            + ",queryEndDate:" + queryEndDate
                            + ",res.size:" + res.size()
                            + ",dayx:" + dayx
                            + ",dayy:" + dayy);
                }
                continue;
            }
            StockMarketTowDayDiffData data = computeTwoDaysDiff(sd,queryStartDate,queryEndDate,dayx,dayy,res);
            insert(data);
        }

    }

    public StockMarketStaticData compute(StockDetail sd, String startDate, String endDate, Integer days, List<StockMarketData> marketData){
        StockMarketStaticData data = new StockMarketStaticData();
        Integer upDays = 0,downDays=0,tradeCount=0;
        BigDecimal closingPriceDiff = BigDecimal.ZERO,
                allTurnover = BigDecimal.ZERO,
                turnoverRateAvg = BigDecimal.ZERO;
        Gson g = new Gson();

        for(int i=0;i<days;i++){
            if(LogUtil.getLogSwitchByKey(LogSwitchEnums.STOCK_STATIC.getName(), Boolean.FALSE)){
                log.info("compute code:" + sd.getStockCode() + ",date:" + endDate + ",day:" + days + ",依赖数据：" + g.toJson(marketData.get(i)));
            }
            if(marketData.get(i).getClosingPrice().compareTo(marketData.get(i + 1).getClosingPrice()) < 0){
                //走低
                downDays++;
            }else if( marketData.get(i).getClosingPrice().compareTo(marketData.get(i + 1).getClosingPrice()) > 0 ){
                //走高
                upDays++;
            }
            allTurnover = allTurnover.add(marketData.get(i).getTurnover());
            turnoverRateAvg = turnoverRateAvg.add(marketData.get(i).getTurnoverRate());
        }
        closingPriceDiff = marketData.get(0).getClosingPrice().subtract(marketData.get(days).getClosingPrice());
        turnoverRateAvg = turnoverRateAvg.divide(BigDecimal.valueOf(days),6, BigDecimal.ROUND_HALF_UP);
        data.setDays(days);
        data.setAllTurnover(allTurnover);
        data.setDownDays(downDays);
        data.setUpDays(upDays);
        data.setStockCode(sd.getStockCode());
        data.setTradeDate(endDate);
        data.setClosingPriceDiff(closingPriceDiff);
        data.setTradeCount(tradeCount);
        data.setTurnoverRateAvg(turnoverRateAvg);

        return data;
    }


    public StockMarketTowDayDiffData computeTwoDaysDiff(
            StockDetail sd, String startDate, String endDate, Integer dayx, Integer dayy, List<StockMarketData> marketData){
        StockMarketTowDayDiffData data = new StockMarketTowDayDiffData();
        BigDecimal daysDiff = BigDecimal.ZERO,dayyDiff=BigDecimal.ZERO,diffpercent=BigDecimal.ZERO;
        BigDecimal closingPriceDiff = BigDecimal.ZERO;

        StockMarketData dataStart = marketData.get(0);
        StockMarketData dataX = marketData.get(dayx);
        StockMarketData dataY = marketData.get(dayy);

        data.setStockCode(sd.getStockCode());
        data.setTradeDate(endDate);
        data.setDaysX(dayx);
        data.setDaysY(dayy);

        data.setClosingPriceDiffX( dataStart.getClosingPrice().subtract(dataX.getClosingPrice()) );
        data.setClosingPriceDiffY( dataX.getClosingPrice().subtract(dataY.getClosingPrice()) );
        if(dataX.equals(BigDecimal.ZERO)){
            closingPriceDiff = BigDecimal.valueOf(1000000);
        }else{
            closingPriceDiff = dataY.getClosingPrice().divide(dataX.getClosingPrice(),BigDecimal.ROUND_HALF_UP);
        }
        data.setClosingPriceDiffPercent(closingPriceDiff);

        return data;
    }

    public void insert(StockMarketStaticData data){
        stockMarketStaticDataMapper.insert(data);
    }

    public void insert(StockMarketTowDayDiffData data){
        stockMarketTowDayDiffDataMapper.insert(data);
    }

    /**
     * 查询本地全部股票基础信息
     * @return
     */
    public List<StockDetail> getLocalAllStock(){
        return stockDetailMapper.selectAll();
    }

    /**
     * 基于时间做排序
     * @param p
     * @return
     */
    List<StockMarketData> sortByDate(List<StockMarketData> p){
        return p;
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

}
