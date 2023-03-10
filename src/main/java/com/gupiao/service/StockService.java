package com.gupiao.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gupiao.bean.api.StockCode;
import com.gupiao.bean.api.StockMsg;
import com.gupiao.enums.ApiUrlPath;
import com.gupiao.generator.domain.*;
import com.gupiao.generator.mapper.*;
import com.gupiao.service.thread.StockMarketDataIncrementalThread;
import com.gupiao.service.thread.StockMarketDataThread;
import com.gupiao.service.thread.StockServiceThread;
import com.gupiao.util.BeanTransformation;
import com.gupiao.util.DateUtils;
import com.gupiao.util.StaticValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * 股票基础数据
 */
@Component
@Slf4j
public class StockService {

    @Autowired
    StockDetailMapper stockDetailMapper;

    @Autowired
    IndustryTransactionsMapper industryTransactionsMapper;

    @Autowired
    StockMarketDataMapper stockMarketDataMapper;

    @Autowired
    SysSettingMapper sysSettingMapper;

    /**
     * 获取全部股票基础数据
     */
    public List<StockDetail> updateAllStockMsg(int threadCount){

        String res;
        List<StockDetail> stockDetailList = new LinkedList<>();
        try {

            res = HttpService.getDataFromUrl(ApiUrlPath.STOCK_INFO_A_CODE_NAME,null);
            //res 格式: [{"code":"871981","name":"晶赛科技"},{"code":"872925","name":"锦好医疗"}]
            List<StockCode> r = new Gson().fromJson(res,new TypeToken<List<StockCode>>() {}.getType());
            if(threadCount < 2){//单线程模式
                for ( StockCode code : r ) {
                    StockDetail detail = new StockDetail();

                    //1.获取股票详细信息
                    HashMap map = new HashMap();
                    map.put("CODE_ID",code.getCode());
                    String resStock = HttpService.getDataFromUrl(ApiUrlPath.STOCK_INDIVIDUAL_INFO_EM,map);
                    List<StockMsg> detailStock = new Gson().fromJson(resStock,new TypeToken<List<StockMsg>>() {}.getType());

                    //2.解析查询结果
                    detail = BeanTransformation.createStockDetailFromList(detailStock);


                    //0.检查股票是否已经存在
                    StockDetail detailTmp = stockDetailMapper.selectByCode(code.getCode());
                    if(null != detailTmp && null != detailTmp.getStockCode()){
                        //存在则更新
                        //log.info("更新股票 code:" + detail.getStockCode());
                        detail.setId(detailTmp.getId());
                        stockDetailMapper.insert(detail);
                    }else{
                        //不存在则插入
                        log.info("新增Code code:" + detail.getStockCode());
                        stockDetailMapper.insert(detail);
                    }

                }

            }else{//按照多线程模式执行

                //计算每个执行的股票个数
                Integer everyThreadCount = r.size()/threadCount;
                Integer tmp = 0;
                List<StockCode> tmpList = new LinkedList<>();

                for (StockCode code:r) {

                    tmpList.add(code);
                    if(tmp <= everyThreadCount){
                        tmp++;
                    }else{
                        StockServiceThread t = new StockServiceThread();
                        t.setStockDetailMapper(stockDetailMapper);
                        t.setLists(tmpList);
                        t.start();
                        tmpList = new LinkedList<>();
                        tmp = 0;
                    }
                }
                //处理最后一个
                StockServiceThread t = new StockServiceThread();
                t.setStockDetailMapper(stockDetailMapper);
                t.setLists(tmpList);
                t.start();

            }

        } catch (IOException e) {
            log.error("StockService--getAllStockMsg出错",e);
        }
        return stockDetailList;
    }

    /**
     * 获取某个股票的详细信息
     * @param code
     * @return
     */
    public StockDetail getStockDetailFromCode(String code){
        StockDetail detail = new StockDetail();
        try {
            HashMap map = new HashMap();
            map.put("CODE_ID",code);
            String res = HttpService.getDataFromUrl(ApiUrlPath.STOCK_INDIVIDUAL_INFO_EM,map);
            //res 格式: [
            // {"item":"总市值","value":4654623527.64},
            // {"item":"流通市值","value":4654623527.64},
            // {"item":"行业","value":"食品饮料"},
            // {"item":"上市时间","value":20161012},
            // {"item":"股票代码","value":"603777"},
            // {"item":"股票简称","value":"来伊份"},
            // {"item":"总股本","value":336559908.0},
            // {"item":"流通股","value":336559908.0}]
            List<StockMsg> r = new Gson().fromJson(res,new TypeToken<List<StockMsg>>() {}.getType());
            detail = BeanTransformation.createStockDetailFromList(r);

        } catch (IOException e) {
            log.error("StockService--getAllStockMsg出错", e);
        }
        return detail;
    }

    /**
     * 获取行业交易信息，参数为年月
     * @return
     */
    public void getIndustryTransactions(String date){

        try {
            Map<String, String> pa = new HashMap<>();
            pa.put("DATE_PARAMS", date);
            String res = null;
            try {
                res = HttpService.getDataFromUrl(ApiUrlPath.STOCK_SZSE_SECTOR_SUMMARY, pa);
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Map<String, String>> resList = new Gson().fromJson(res, new TypeToken<List<Map<String, String>>>() {}.getType());
            for (Map<String,String> map: resList) {
                IndustryTransactions b = BeanTransformation.createIndustryTransactionsFromList(map);
                b.setTradeDate("202201");
                industryTransactionsMapper.insert(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getIndustryTransactions error",e);
        }

    }

    /**
     * 更新全部股票的交易信息
     */
    public void updateStockMarketAllData(int threadCount){

        SysSetting setting = sysSettingMapper.selectByCode(StaticValue.UPDATE_ALL_STOCK_DATA_KEY);
        String nowDate = DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE5);
        if(null != setting){
            if(nowDate.equals(setting.getSysValue())){
                log.info("本日已更新全量数据，无法再次更新");
                return;
            }else{
                sysSettingMapper.updateByKey(StaticValue.UPDATE_ALL_STOCK_DATA_KEY,nowDate);
            }

        }else{
            setting = new SysSetting();
            setting.setSysKey(StaticValue.UPDATE_ALL_STOCK_DATA_KEY);
            setting.setSysValue(nowDate);
            setting.setDes("");
            sysSettingMapper.insert(setting);
        }

        String res;
        try {
            //1.获取全部股票信息
            res = HttpService.getDataFromUrl(ApiUrlPath.STOCK_INFO_A_CODE_NAME,null);
            //res 格式: [{"code":"871981","name":"晶赛科技"},{"code":"872925","name":"锦好医疗"}]
            List<StockCode> r = new Gson().fromJson(res,new TypeToken<List<StockCode>>() {}.getType());
            //2.逐个股票获取交易信息

            if(threadCount < 2){//单线程模式
                for (StockCode code:r) {
                    this.getStockMarketAllDataByCode(code.getCode());
                }
            }else{//按照多线程模式执行

                //计算每个执行的股票个数
                Integer everyThreadCount = r.size()/threadCount;
                Integer tmp = 0;
                List<StockCode> tmpList = new LinkedList<>();

                for (StockCode code:r) {

                    tmpList.add(code);
                    if(tmp <= everyThreadCount){
                        tmp++;
                    }else{
                        StockMarketDataThread t = new StockMarketDataThread();
                        t.setStockService(this);
                        t.setLists(tmpList);
                        t.start();
                        tmpList = new LinkedList<>();
                        tmp = 0;
                    }
                }
                //处理最后一个
                StockMarketDataThread t = new StockMarketDataThread();
                t.setStockService(this);
                t.setLists(tmpList);
                t.start();

            }


        }catch (Exception e){
            log.error("updateStockMarketAllData--error",e);
        }

    }

    /**
     * 通过股票code获取，股票历史交易数据 复权后
     * @param code
     */
    public void getStockMarketAllDataByCode(String code){

        log.info("线程ID:" + Thread.currentThread().getId() + "获取股票:" + code + "全部交易信息。");

        Map<String, String> pa = new HashMap<>();
        pa.put("CODE_ID", code);
        String res = null;
        try {
            res = HttpService.getDataFromUrl(ApiUrlPath.STOCK_ZH_A_HIST_ALL, pa);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Map<String, String>> resList = new Gson().fromJson(res, new TypeToken<List<Map<String, String>>>() {}.getType());
        List<StockMarketData> records = new LinkedList<>();
        for (Map<String,String> map: resList) {
            StockMarketData b = BeanTransformation.createStockMarketDataFromList(map);
            b.setStockCode(code);
            records.add(b);
        }
        stockMarketDataMapper.batchInsert(records);
    }

    /**
     * @param threadCount
     * @param sync 多线程模式起效，true 执行线程会执行一批任务，false 执行线程启动工作线程后立刻退出
     */
    public void getAllStockYesterdayData(int threadCount,Boolean sync){

        SysSetting setting = sysSettingMapper.selectByCode(StaticValue.UPDATE_STOCK_DATA_KEY);
        if(null != setting){
            sysSettingMapper.updateByKey(StaticValue.UPDATE_STOCK_DATA_KEY,DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE2));
        }else{
            setting = new SysSetting();
            setting.setSysKey(StaticValue.UPDATE_STOCK_DATA_KEY);
            setting.setSysValue(DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE2));
            setting.setDes("");
            sysSettingMapper.insert(setting);
        }

        String res;
        try {
            //1.获取全部股票信息
            res = HttpService.getDataFromUrl(ApiUrlPath.STOCK_INFO_A_CODE_NAME,null);
            List<StockCode> r = new Gson().fromJson(res,new TypeToken<List<StockCode>>() {}.getType());
            //2.逐个股票获取交易信息

            if(threadCount < 2){//单线程模式
                for (StockCode code:r) {
                    this.getStockYesterdayDataByCode(code.getCode());
                }
            }else{//按照多线程模式执行

                //计算每个执行的股票个数
                Integer everyThreadCount = r.size()/threadCount;
                Integer tmp = 0;
                List<StockCode> tmpList = new LinkedList<>();

                for (StockCode code:r) {

                    tmpList.add(code);
                    if(tmp <= everyThreadCount){
                        tmp++;
                    }else{
                        StockMarketDataThread t = new StockMarketDataThread();
                        t.setStockService(this);
                        t.setLists(tmpList);
                        if(Boolean.TRUE.equals(sync)){
                            t.run();
                        }else{
                            t.start();
                        }
                        tmpList = new LinkedList<>();
                        tmp = 0;
                    }
                }
                //处理最后一个
                StockMarketDataIncrementalThread t = new StockMarketDataIncrementalThread();
                t.setStockService(this);
                t.setLists(tmpList);
                t.start();

            }

        }catch (Exception e){
            log.error("getAllStockYesterdayData--error",e);
        }

    }

    public void getStockYesterdayDataByCode(String code) throws Exception{

        StockMarketData stockMarketData = stockMarketDataMapper.selectMaxDate(code);
        ApiUrlPath path = null;
        Map<String,String> params = new HashMap<>();
        if(null == stockMarketData){
            //数据内没有对应股票数据，需要抓取全部数据
            //path = ApiUrlPath.STOCK_ZH_A_HIST_ALL;
            path = ApiUrlPath.STOCK_ZH_A_HIST;


            //截止时间是T-1天
            String endDate = DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE4);

            params.put("CODE_ID", code);
            params.put("START_DATE", "20210101");
            params.put("END_DATE", endDate);

        }else{
            //数据库内有数据，只需要抓取后续的数据
            path = ApiUrlPath.STOCK_ZH_A_HIST;
            params.put("CODE_ID", code);
            //修正时间格式
            stockMarketData.setTradeDate(stockMarketData.getTradeDate().replace("-",""));
            //截止时间是T-1天
            String endDate = DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE4);
            endDate = DateUtils.dateAddDays(endDate,DateUtils.DATE_FORMATE4,-1L);
            String startDate = stockMarketData.getTradeDate();
            if(startDate.equals(endDate)){
                return;
            }
            //已经存在的数据不用补助
            startDate = DateUtils.dateAddDays(stockMarketData.getTradeDate(),DateUtils.DATE_FORMATE4,1L);

            params.put("START_DATE", startDate);
            params.put("END_DATE", endDate);
        }

        String res = "";
        try {
            log.info("code = " + code + new Gson().toJson(params));
            res = HttpService.getDataFromUrl(path, params);
        } catch (IOException e) {
            log.error("getStockYesterdayDataByCode 出现错误:" ,e);
            e.printStackTrace();
            return;
        }

        List<Map<String, String>> resList = new Gson().fromJson(res, new TypeToken<List<Map<String, String>>>() {}.getType());
        List<StockMarketData> records = new LinkedList<>();
        for (Map<String,String> map: resList) {
            StockMarketData b = BeanTransformation.createStockMarketDataFromList(map);
            b.setStockCode(code);
            records.add(b);
        }
        if(null == records || records.size() < 1){
            return;
        }
        stockMarketDataMapper.batchInsert(records);

    }


}
