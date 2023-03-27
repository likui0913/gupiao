package com.gupiao.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gupiao.bean.api.StockMsg;
import com.gupiao.enums.ApiUrlPath;
import com.gupiao.enums.LogSwitchEnums;
import com.gupiao.generator.domain.*;
import com.gupiao.generator.mapper.*;
import com.gupiao.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.Executor;

@Component
@Slf4j
public class UpdateStockDailySaleService {

    @Autowired
    StockDetailMapper stockDetailMapper;

    @Autowired
    IndustryTransactionsMapper industryTransactionsMapper;

    @Autowired
    StockMarketDataMapper stockMarketDataMapper;

    @Autowired
    SysSettingMapper sysSettingMapper;

    @Autowired
    StockMarketRuntimeDataMapper stockMarketRuntimeDataMapper;

    @Autowired
    StockDetailOrderAllMapper stockDetailOrderAllMapper;

    @Autowired
    ThreadPoolTaskExecutor stockDetailOrderExecutor;

    @Autowired
    StockDetailOrderPropertiesStaticMapper stockDetailOrderPropertiesStaticMapper;

    public void updateAllStockDailySale(){

        Long startTime = System.currentTimeMillis();
        List<StockDetail> localAllStock = getLocalAllStock();
        Integer updateCount = 0,jumpCount = 0,errorCount = 0;
        for (StockDetail sd:localAllStock) {
            try {
                if(LogUtil.getLogSwitchByKey(LogSwitchEnums.STOCK_TRADE.getName(), Boolean.FALSE)){
                    log.info("updateAllStockDailySale 处理code:" + sd.getStockCode());
                }
                Integer s = this.updateStockDailySaleByCode(sd);
                if(0==s){
                    updateCount++;
                }else if(1 == s){
                    jumpCount++;
                }else {
                    errorCount++;
                }
            }catch (Exception e){
                log.error("updateAllStockDailySale 处理出错，code=" + sd.getStockCode(),e);
                errorCount++;
            }

        }
        String updateMsg = "updateCount=" + updateCount + ",jumpCount=" + jumpCount + ",errorCount=" + errorCount;
        DingUtil.sendDingTalk("更新stock交易信息完成:" + updateMsg);
        log.info("updateAllStockDailySale 处理完成," + updateMsg);
        log.info("updateAllStockDailySale 处理完成，时间花费：" + (System.currentTimeMillis() - startTime));

    }

    /**
     * 查询本地全部股票基础信息
     * @return
     */
    public List<StockDetail> getLocalAllStock(){
        return stockDetailMapper.selectAll();
    }

    /**
     * 按照给定股票信息，处理更新对应交易信息
     * @param stockDetail
     */
    protected Integer updateStockDailySaleByCode(StockDetail stockDetail) throws Exception{
        if(stockDetail.getStockType().equals(0)){
            //处理国内股票信息
            return this.updateChinaStockByCode(stockDetail);
        }else{
            //处理美股信息
            return this.updateUSAStockByCode(stockDetail);
        }

    }

    /**
     * 更新国内股票交易信息
     * @param stockDetail
     * @return 0=正常处理，1=跳过，2=出错
     */
    protected Integer updateChinaStockByCode(StockDetail stockDetail) throws Exception {

        //从数据库读取最新的数据
        StockMarketData data = stockMarketDataMapper.selectMaxDate(stockDetail.getStockCode());
        //dbEndDate代表数据库内已有的最新的数据
        String dbEndDate = StaticValue.STOCK_DAILY_SALE_DATE_DEFAULT;
        if(null != data){
            dbEndDate = data.getTradeDate();
        }
        String endDateNow = DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE5);
        //String endDateT_1 = DateUtils.dateAddDays(endDateNow,DateUtils.DATE_FORMATE5, -1L);
        //计算从默认值到当前的天数
        Integer dayDiff = DateUtils.daysDiff(dbEndDate,endDateNow,DateUtils.DATE_FORMATE5);
        if( dayDiff <= 0){
            return 1;
        }
        //dbEndDate代表数据库内已经有的最后一条数据，更新数据时要以dbEndDate+1为开始时间
        dbEndDate = DateUtils.dateAddDays(dbEndDate,DateUtils.DATE_FORMATE5,1L);
        List<StockMarketData> stockMarketDataList = this.getChinaSaleByDateAndCode(stockDetail.getStockCode(),dbEndDate,endDateNow);
        this.insertStockSaleDataToDb(stockMarketDataList);
        return 0;

    }

    /**
     * 更新美股股票交易信息
     * @param stockDetail
     * @return
     */
    protected Integer updateUSAStockByCode(StockDetail stockDetail){
        return 1;
    }

    /**
     * 根据股票信息，获取更新进度，如果没查询到说明是新增股票，手动插入默认时间
     * @param stockDetail
     * @return
     */
    protected SysSetting getSaleDateOrCreateByCode(StockDetail stockDetail){
        String key = stockDetail.getStockCode() + StaticValue.STOCK_DAILY_SALE_DATE_KEY;
        SysSetting setting = sysSettingMapper.selectByCode(key);
        if(null == setting){
            setting = new SysSetting();
            setting.setSysKey(key);
            setting.setSysValue(StaticValue.STOCK_DAILY_SALE_DATE_DEFAULT);
            setting.setDes("");
            sysSettingMapper.insert(setting);
        }
        return setting;
    }

    /**
     * 获取国内指定股票在指定时间段内的历史交易信息
     * @param code
     * @param startDate
     * @param endDate
     */
    protected List<StockMarketData> getChinaSaleByDateAndCode(String code,String startDate,String endDate) throws Exception{
        Map<String,String> params = new HashMap<>();
        params.put("CODE_ID", code);
        params.put("START_DATE", startDate.replace("-",""));
        params.put("END_DATE", endDate.replace("-",""));
        if(LogUtil.getLogSwitchByKey(LogSwitchEnums.STOCK_TRADE.getName(), Boolean.FALSE)){
            log.info("getChinaSaleByDateAndCode--params:{},url:{}",params,ApiUrlPath.STOCK_ZH_A_HIST);
        }
        String res = HttpService.getDataFromUrl(ApiUrlPath.STOCK_ZH_A_HIST, params);
        List<Map<String, String>> resList = new Gson().fromJson(res, new TypeToken<List<Map<String, String>>>() {}.getType());
        List<StockMarketData> records = new LinkedList<>();
        for (Map<String,String> map: resList) {
            StockMarketData b = BeanTransformation.createStockMarketDataFromList(map);
            b.setStockCode(code);
            records.add(b);
        }
        return records;
    }

    /**
     * 将股票交易数据插入到数据库
     */
    protected void insertStockSaleDataToDb(List<StockMarketData> stockMarketDataList){
        if(null == stockMarketDataList || stockMarketDataList.size() == 0){
            return;
        }
        stockMarketDataMapper.batchInsert(stockMarketDataList);
    }

    /**
     * 根据code，更新对应的历史交易数据更新进度
     */
    protected void updateStockDailySettingByCode(SysSetting sysSetting){
        sysSettingMapper.updateByKey(sysSetting.getSysKey(),sysSetting.getSysValue());
    }

    /**
     * 获取国内指定股票在指定时间段内的历史交易信息
     * @param code
     * @param startDate
     * @param endDate
     */
    protected List<StockMarketData> getUSASaleByDateAndCode(String code,String startDate,String endDate)throws Exception {
        return null;
    }

    /**
     * 刷新实时全量交易数据
     */
    public void renovateNowTradeDate(){

        //1 获取当前数据
        List<StockMarketRuntimeData> res = getNowTradeData();

        //2 解析时间
        String nowDate = DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE5);
        String nowDateTime = DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE2);

        //3 删除数据,每天会有多次更新，不再删除当天的历史记录
        //stockMarketRuntimeDataMapper.deleteByDate(nowDate);

        //4 全量数据写入
        stockMarketRuntimeDataMapper.batchInsert(res);

        //5 发送通知并记录日志
        DingUtil.sendDingTalk("刷新实时全量数据完成，条数:" + res.size());
        log.info("刷新实时全量数据，条数:" + res.size());

    }

    /**
     * 从api获取数据并组装成数据bean格式
     * @return
     */
    public List<StockMarketRuntimeData> getNowTradeData(){

        List<StockMarketRuntimeData> res = new LinkedList<>();
        String nowTime = DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE2);
        try {
            String tradeDataString = HttpService.getDataFromUrl(ApiUrlPath.STOCK_ZH_A_SPOT_EM,null);
            List<Map<String, String>> resList = new Gson().fromJson(tradeDataString, new TypeToken<List<Map<String, String>>>() {}.getType());
            for (Map<String,String> map: resList) {
                StockMarketRuntimeData sd = BeanTransformation.createStockMarketRuntimeDataFromList(map);
                sd.setTradeDate(DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE5));
                sd.setTradeTime(nowTime);
                res.add(sd);
            }
        }catch (Exception e){
            log.error("getNowTradeData 出现错误。",e);
        }

        return res;
    }

    /**
     * 刷新股票在当天的交易明细信息，细化到交易笔数，花费时间很长，谨慎调用！！！
     */
    public void reStockDetailTradeDate(){

        //1.获取当天时间
        String nowDate = DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE5);

        //2.获取全量code信息
        List<StockDetail> localAllStock = getLocalAllStock();

        Integer success = 0,zeroCount = 0;
        for (StockDetail sd:localAllStock) {

            //3.逐个code调用查询
            if(sd.getStockType().equals(0)){
                //reStockDetailTradeDateByCode(sd.getStockCode(),nowDate);
                success++;
                //多线程模式运行
                stockDetailOrderExecutor.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            reStockDetailTradeDateByCode(sd.getStockCode(),nowDate);
                        } catch (Exception e) {
                            log.error("reStockDetailTradeDateByCode-多线程出现错误！",e);
                        }
                    }
                });

            }else{
                //美股，不做处理
            }

        }

        DingUtil.sendDingTalk("刷新code每天每笔交易明细数据完成，success=" + success + ",zeroCount=" + zeroCount);

    }

    public void reStockDetailTradeDateByCode(String code,String date){

        //处理国内股票信息
        List<StockDetailOrderAll> allData = this.getStockDetailOrderAllDateFromDataApi(code,date);

        //4.会写数据库
        if(null == allData || allData.size() == 0){
        }else{

            //判断是否想写入明细
            SysSetting setting = sysSettingMapper.selectByCode("insertStockDetailOrderSwitch");
            if(null == setting || "0".equals(setting.getSysValue())) {

            }else{
                stockDetailOrderAllMapper.batchInsert(allData);
            }

            //写入汇总数据
            StockDetailOrderPropertiesStatic propertiesStatic = new StockDetailOrderPropertiesStatic();
            propertiesStatic.setStockCode(code);
            propertiesStatic.setTradeDate(date);
            propertiesStatic.setTradeTime("00:00:00");
            for (StockDetailOrderAll orderAll:allData) {
                if(orderAll.getProperties().equals(0)){
                    propertiesStatic.propertiesUnbiasedAdd1();
                }else if(orderAll.getProperties().equals(1)){
                    propertiesStatic.propertiesUpAdd1();
                }else{
                    propertiesStatic.propertiesDownAdd1();
                }
            }
            stockDetailOrderPropertiesStaticMapper.insert(propertiesStatic);

        }

    }

    /**
     * 通过code获取当天交易明细数据
     * @param code
     * @return
     */
    public List<StockDetailOrderAll> getStockDetailOrderAllDateFromDataApi(String code,String date){

        List<StockDetailOrderAll> resData = new LinkedList<>();

        String jys = Util.codeToExchange(code);
        HashMap map = new HashMap();
        map.put("CODE_ID",jys + code);
        log.info("getStockDetailOrderAllDateFromDataApi,code:" + map.get("CODE_ID") );
        if(LogUtil.getLogSwitchByKey(LogSwitchEnums.STOCK_DETAIL_ORDER_ALL.getName(), Boolean.FALSE)){
            //log.info("getStockDetailOrderAllDateFromDataApi,code:" + map.get("CODE_ID") );
        }

        String resStock = null;
        try {
            resStock = HttpService.getDataFromUrl(ApiUrlPath.STOCK_ZH_A_TICK_TX_JS,map);
            List<Map<String, String>> resList = new Gson().fromJson(resStock, new TypeToken<List<Map<String, String>>>() {}.getType());
            for (Map<String, String> ob:resList) {
                StockDetailOrderAll orderAll = BeanTransformation.createStockDetailOrderAllDataFromList(ob);
                orderAll.setTradeDate(date);
                orderAll.setStockCode(code);
                resData.add(orderAll);
            }
        } catch (IOException e) {
            log.error("getStockDetailOrderAllDateFromDataApi 出现错误!" ,e);
        }

        return resData;

    }

}
