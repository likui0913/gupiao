package com.gupiao.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gupiao.enums.ApiUrlPath;
import com.gupiao.generator.domain.StockDetail;
import com.gupiao.generator.domain.StockMarketData;
import com.gupiao.generator.domain.SysSetting;
import com.gupiao.generator.mapper.IndustryTransactionsMapper;
import com.gupiao.generator.mapper.StockDetailMapper;
import com.gupiao.generator.mapper.StockMarketDataMapper;
import com.gupiao.generator.mapper.SysSettingMapper;
import com.gupiao.util.BeanTransformation;
import com.gupiao.util.DateUtils;
import com.gupiao.util.StaticValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;

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

    public void updateAllStockDailySale(){
        Long startTime = System.currentTimeMillis();
        List<StockDetail> localAllStock = getLocalAllStock();
        Integer updateCount = 0,jumpCount = 0,errorCount = 0;
        for (StockDetail sd:localAllStock) {
            try {
                //log.info("updateAllStockDailySale 处理code:" + sd.getStockCode());
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
        log.info("updateAllStockDailySale 处理完成，updateCount=" + updateCount + ",jumpCount=" + jumpCount + ",errorCount=" + errorCount);
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

        SysSetting setting = this.getSaleDateOrCreateByCode(stockDetail);
        String endDateNow = DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE5);
        String endDateT_1 = DateUtils.dateAddDays(endDateNow,DateUtils.DATE_FORMATE5, -1L);
        //计算从默认值到t-1的天数
        Integer dayDiff = DateUtils.daysDiff(setting.getSysValue(),endDateT_1,DateUtils.DATE_FORMATE5);
        if( dayDiff < 0){
            return 1;
        }
        List<StockMarketData> stockMarketDataList = this.getChinaSaleByDateAndCode(stockDetail.getStockCode(),setting.getSysValue(),endDateT_1);
        this.insertStockSaleDataToDb(stockMarketDataList);
        setting.setSysValue(endDateNow);
        this.updateStockDailySettingByCode(setting);
        Thread.sleep(1000*2);
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
        log.info("params:{},url:{}",params,ApiUrlPath.STOCK_ZH_A_HIST);
        String res = HttpService.getDataFromUrl(ApiUrlPath.STOCK_ZH_A_HIST, params);
        log.info("params:{},url:{},res.length:{}",params,ApiUrlPath.STOCK_ZH_A_HIST,res.length());
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

    public static void main(String[] args) {
        List<Map<String, String>> resList = new Gson().fromJson("[]", new TypeToken<List<Map<String, String>>>() {}.getType());
        List<StockMarketData> records = new LinkedList<>();
        for (Map<String,String> map: resList) {
            StockMarketData b = BeanTransformation.createStockMarketDataFromList(map);
            b.setStockCode("111");
            records.add(b);
        }
    }
}
