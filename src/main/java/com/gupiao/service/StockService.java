package com.gupiao.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gupiao.bean.api.StockCode;
import com.gupiao.bean.api.StockMsg;
import com.gupiao.enums.ApiUrlPath;
import com.gupiao.generator.domain.IndustryTransactions;
import com.gupiao.generator.domain.StockDetail;
import com.gupiao.generator.domain.StockMarketData;
import com.gupiao.generator.domain.SysSetting;
import com.gupiao.generator.mapper.IndustryTransactionsMapper;
import com.gupiao.generator.mapper.StockDetailMapper;
import com.gupiao.generator.mapper.StockMarketDataMapper;
import com.gupiao.generator.mapper.SysSettingMapper;
import com.gupiao.service.stock.StockMarketDataThread;
import com.gupiao.service.stock.StockServiceThread;
import com.gupiao.util.BeanTransformation;
import com.gupiao.util.DateUtils;
import com.gupiao.util.StaticValue;
import lombok.extern.slf4j.Slf4j;
import org.omg.PortableServer.THREAD_POLICY_ID;
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

                    //0.检查股票是否已经存在
                    StockDetail detailTmp = stockDetailMapper.selectByCode(code.getCode());
                    if(null != detailTmp && null != detailTmp.getStockCode()){
                        continue;
                    }

                    //1.获取股票详细信息
                    HashMap map = new HashMap();
                    map.put("CODE_ID",code.getCode());
                    String resStock = HttpService.getDataFromUrl(ApiUrlPath.STOCK_INDIVIDUAL_INFO_EM,map);
                    List<StockMsg> detailStock = new Gson().fromJson(resStock,new TypeToken<List<StockMsg>>() {}.getType());

                    //2.解析查询结果
                    detail = BeanTransformation.createStockDetailFromList(detailStock);
                    log.info("新增股票 code:" + detail.getStockCode());
                    stockDetailMapper.insert(detail);

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

    public static void main(String[] args) {
        DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE5);
    }

}
