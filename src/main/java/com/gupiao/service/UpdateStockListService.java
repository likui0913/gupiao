package com.gupiao.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gupiao.bean.api.StockCode;
import com.gupiao.bean.api.StockMsg;
import com.gupiao.enums.ApiUrlPath;
import com.gupiao.enums.LogSwitchEnums;
import com.gupiao.generator.domain.StockDetail;
import com.gupiao.generator.mapper.IndustryTransactionsMapper;
import com.gupiao.generator.mapper.StockDetailMapper;
import com.gupiao.generator.mapper.StockMarketDataMapper;
import com.gupiao.generator.mapper.SysSettingMapper;
import com.gupiao.util.BeanTransformation;
import com.gupiao.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 更新全部股票信息
 */
@Component
@Slf4j
public class UpdateStockListService {

    @Autowired
    StockDetailMapper stockDetailMapper;

    @Autowired
    IndustryTransactionsMapper industryTransactionsMapper;

    @Autowired
    StockMarketDataMapper stockMarketDataMapper;

    @Autowired
    SysSettingMapper sysSettingMapper;

    /**
     * 更新全部股票信息
     */
    public void updateAllStockMsg(){
        log.info("开始更新全部Coede的基础信息");
        Long startTime = System.currentTimeMillis();
        Map<String,StockDetail> localAllStock = this.getLocalAllStock();
        log.info("更新全部Code信息，获取本地全部Code信息，花费 " + (System.currentTimeMillis()-startTime)/1000 + " 秒,获取股票数量：" + localAllStock.size() + "个。");
        updateChinaStockMsg(localAllStock);
        log.info("更新全部Code信息，处理中国全部Code，花费 " + (System.currentTimeMillis()-startTime)/1000 + " 秒");
        updateUSAStockMsg(localAllStock);
        Long endTime = System.currentTimeMillis();
        log.info("更新全部Code信息完成，花费 " + (endTime-startTime)/1000 + " 秒");
    }

    /**
     * 更新中国全部股票信息
     */
    public void updateChinaStockMsg(Map<String,StockDetail> localAllStock){

        List<StockCode> allRemoteChinaStock = this.getRemoteChinaAllStock();

        int updateCount = 0,insertCount = 0,errorCount=0;
        log.info("接口获取中国全部Code信息数量:" + allRemoteChinaStock.size());
        for (StockCode code:allRemoteChinaStock) {

            StockDetail dbStock = null;
            //查找在数据库内是否都存在对应股票信息
            if(localAllStock.containsKey(code.getCode())){
                dbStock = localAllStock.get(code.getCode());
            }

            StockDetail stockDetail = getChinaStockDetailByCode(code.getCode());
            if(dbStock != null){
                //更新
                updateCount++;
                stockDetail.setStockType(dbStock.getStockType());
                stockDetail.setId(dbStock.getId());
                this.updateStock(stockDetail);
                if(LogUtil.getLogSwitchByKey(LogSwitchEnums.STOCK_MSG.getName(), Boolean.FALSE)){
                    log.info("更新Code:" + stockDetail.getStockCode());
                }
            }else {
                //新增
                insertCount++;
                stockDetail.setStockType(0);
                if(LogUtil.getLogSwitchByKey(LogSwitchEnums.STOCK_MSG.getName(), Boolean.FALSE)){
                    log.info("新增Code:" + stockDetail.getStockCode());
                }

                this.insertNewStock(stockDetail);
            }
        }
        log.info("updateChinaStockMsg 更新完成，updateCount=" + updateCount +"，insertCount=" + insertCount);

    }

    /**
     * 更新美股全部股票信息
     */
    public void updateUSAStockMsg(Map<String,StockDetail> localAllStock){

        List<StockCode> allRemoteChinaStock = this.getRemoteUSAAllStock();
        StockDetail dbStock = null;

        int updateCount = 0,insertCount = 0,errorCount=0;

        for (StockCode code:allRemoteChinaStock) {

            if(localAllStock.containsKey(code.getCode())){
                dbStock = localAllStock.get(code.getCode());
            }

            if(dbStock == null){
                //新增
                insertCount++;

                StockDetail stockDetail = new StockDetail();
                stockDetail.setStockCode(code.getCode());
                stockDetail.setStockName(code.getName());
                stockDetail.setStockType(1);
                stockDetail.setTotalCapital((double) 0);
                stockDetail.setCirculatingCapital((double) 0);
                stockDetail.setIndustry("");
                stockDetail.setMarketTime("");
                stockDetail.setCirculatingCapitalization((double) 0);
                stockDetail.setTotalCapitalization((double) 0);
                stockDetail.setIsFocus(0);
                this.insertNewStock(stockDetail);
            }
        }
        log.info("updateUSAStockMsg 更新完成，updateCount=" + updateCount +"，insertCount=" + insertCount);

    }

    /**
     * 查询本地全部股票基础信息
     * @return
     */
    public Map<String,StockDetail> getLocalAllStock(){
        Map<String,StockDetail> res = new HashMap<>();
        List<StockDetail> dbRes = stockDetailMapper.selectAll();
        log.info("dbRes size is " + dbRes.size());
        for (StockDetail sd:dbRes) {
            res.put(sd.getStockCode(),sd);
        }
        return res;
    }

    /**
     * 获取远端全部的中国股票信息
     * @return
     */
    public List<StockCode> getRemoteChinaAllStock(){
        try{
            String res = HttpService.getDataFromUrl(ApiUrlPath.STOCK_INFO_A_CODE_NAME,null);
            //res 格式: [{"code":"871981","name":"晶赛科技"},{"code":"872925","name":"锦好医疗"}]
            List<StockCode> r = new Gson().fromJson(res,new TypeToken<List<StockCode>>() {}.getType());
            return r;
        }catch (Throwable t){
            log.error("updateChinaStockMsg 出现错误！",t);
            return null;
        }
    }

    /**
     * 根据code获取中国股票的详细信息
     * @param code
     * @return
     */
    public StockDetail getChinaStockDetailByCode(String code){
        try{
        StockDetail detail = new StockDetail();
        Map<String ,String> map = new HashMap<String ,String>();
        map.put("CODE_ID",code);
        String resStock = HttpService.getDataFromUrl(ApiUrlPath.STOCK_INDIVIDUAL_INFO_EM, map);
        List<StockMsg> detailStock = new Gson().fromJson(resStock,new TypeToken<List<StockMsg>>() {}.getType());

        //2.解析查询结果
        detail = BeanTransformation.createStockDetailFromList(detailStock);
        return detail;
        }catch (Throwable t){
            log.error("getChinaStockDetailByCode 获取中国Code详细信息出错！",t);
            return null;
        }

    }

    /**
     * 获取远端全部的美国股票信息
     * @return
     */
    public List<StockCode> getRemoteUSAAllStock(){

        try {
            List<StockCode> allUSAStockCode = new LinkedList<>();
            String resUSAStock = HttpService.getDataFromUrl(ApiUrlPath.STOCK_US_SPOT_EM, null);
            //[{"序号":1,"名称":"HNR Acquisition Corp Wt","最新价":0.19,"涨跌额":0.15,"涨跌幅":375.0,"开盘价":0.04,"最高价":0.19,"最低价":0.04,"昨收价":0.04,"总市值":null,"市盈率":null,"成交量":661.0,"成交额":44.0,"振幅":375.0,"换手率":null,"代码":"107.HNRA_WS"},
            // {"序号":2,"名称":"Arisz Acquisition Corp Wt","最新价":0.09,"涨跌额":0.07,"涨跌幅":350.0,"开盘价":0.09,"最高价":0.09,"最低价":0.09,"昨收价":0.02,"总市值":null,"市盈率":null,"成交量":100.0,"成交额":9.0,"振幅":0.0,"换手率":null,"代码":"105.ARIZW"}]
            List<Map<String, String>> resList = new Gson().fromJson(resUSAStock, new TypeToken<List<Map<String, String>>>() {}.getType());
            for (Map<String, String> ob:resList) {
                allUSAStockCode.add(BeanTransformation.createUSAStockCodeFromList(ob));
            }
            return allUSAStockCode;
        }catch (Throwable t){
            log.error("getRemoteUSAAllStock error",t);
            return null;
        }
    }

    /**
     * 新增一个股票信息
     * @param sd
     */
    public void insertNewStock(StockDetail sd){
        stockDetailMapper.insert(sd);
    }

    /**
     * 更新一个股票信息
     * @param sd
     */
    public void updateStock(StockDetail sd){
        stockDetailMapper.updateById(sd);
    }

}
