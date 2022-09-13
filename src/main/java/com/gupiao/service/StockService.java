package com.gupiao.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gupiao.bean.api.StockCode;
import com.gupiao.bean.api.StockMsg;
import com.gupiao.enums.ApiUrlPath;
import com.gupiao.generator.domain.StockDetail;
import com.gupiao.generator.mapper.StockDetailMapper;
import com.gupiao.service.stock.StockServiceThread;
import com.gupiao.util.BeanTransformation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 股票基础数据
 */
@Component
@Slf4j
public class StockService {

    @Autowired
    StockDetailMapper stockDetailMapper;

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
    public String getIndustryTransactions(String date){

        String res = null;
        try {
            Map<String,String> pa = new HashMap<>();
            pa.put("DATE_PARAMS","202201");
            res = HttpService.getDataFromUrl(ApiUrlPath.STOCK_SZSE_SECTOR_SUMMARY,pa);
            List<Map<String,String>> resList = new Gson().fromJson(res,new TypeToken<List<Map<String, String>>>() {}.getType());
            for (Map<String,String> resOne : resList) {
                /*
                 {
                    "项目名称": "合计",
                    "项目名称-英文": "Total",
                    "交易天数": 19,
                    "成交金额-人民币元": 11786437756873,
                    "成交金额-占总计": 100.0,
                    "成交股数-股数": 904978116344,
                    "成交股数-占总计": 100.0,
                    "成交笔数-笔": 982714260,
                    "成交笔数-占总计": 100.0
                 }
                */
                System.out.println(resOne.get("项目名称"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("getIndustryTransactions error",e);
        }
        return res;

    }

    public static void main(String[] args) {
        String v = "{\"item\":\"上市时间\",\"value\":20161012}";
        StockMsg r = new Gson().fromJson(v,StockMsg.class);
        Integer s = Double.valueOf(r.getValue().toString()).intValue();
        System.out.println(s.toString());
    }

}
