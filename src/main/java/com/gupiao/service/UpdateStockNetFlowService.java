package com.gupiao.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gupiao.enums.ApiUrlPath;
import com.gupiao.generator.domain.StockMoneyNetFlow;
import com.gupiao.generator.mapper.StockMoneyNetFlowMapper;
import com.gupiao.generator.mapper.SysSettingMapper;
import com.gupiao.util.StaticValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class UpdateStockNetFlowService {

    @Autowired
    SysSettingMapper sysSettingMapper;

    @Autowired
    StockMoneyNetFlowMapper stockMoneyNetFlowMapper;

    /**
     * 北向资金流动金额
     */
    public List<Map<String, String>> getFlowToNorthMoney(){

        try {
            String res = "";
            //1.获取全部股票信息
            res = HttpService.getDataFromUrl(ApiUrlPath.STOCK_HSGT_NORTH_NET_FLOW,null);
            //res 格式: [{"date":"2022-10-20","value":-287759.15},{"date":"2022-10-21","value":-402560.66}]
            List<Map<String, String>> r = new Gson().fromJson(res,new TypeToken<List<Map<String, String>>>() {}.getType());

            return r;

        }catch (Exception e){
            log.error("getFlowToNorthMoney--error",e);
            return new LinkedList<Map<String, String>>();
        }

    }

    /**
     * 南向资金流动金额
     */
    public List<Map<String, String>> getFlowToSouthMoney(){

        try {
            String res = "";
            //1.获取全部股票信息
            res = HttpService.getDataFromUrl(ApiUrlPath.STOCK_HSGT_SOUTH_NET_FLOW,null);
            //res 格式: [{"date":"2022-10-20","value":-287759.15},{"date":"2022-10-21","value":-402560.66}]
            List<Map<String, String>> r = new Gson().fromJson(res,new TypeToken<List<Map<String, String>>>() {}.getType());

            return r;

        }catch (Exception e){
            log.error("getFlowToSouthMoney--error",e);
            return new LinkedList<Map<String, String>>();
        }

    }

    public void insertNetFlowDetail(String type,List<Map<String, String>> dataList){

        String maxDate = "0000-00-00";
        StockMoneyNetFlow flowRow = stockMoneyNetFlowMapper.selectMaxRowByType(type);
        if(null != flowRow){
            maxDate = flowRow.getFlowDate();
        }

        List<StockMoneyNetFlow> insertRows = new LinkedList<>();
        for (Map<String, String> row:dataList) {

            //如果该时间在数据库内已经存在，则跳过插入
            if(maxDate.compareTo(row.get("date")) >= 0){
                return;
            }

            StockMoneyNetFlow rowNew = new StockMoneyNetFlow();
            rowNew.setFlowType(type);
            rowNew.setFlowDate(row.get("date"));
            rowNew.setCount(BigDecimal.valueOf(Double.parseDouble(row.get("value"))));
            insertRows.add(rowNew);
        }

        if(insertRows.size() > 0){
            stockMoneyNetFlowMapper.batchInsert(insertRows);
        }

    }

    public void updateFlowData(){

        //更新
        List<Map<String, String>> resNorth = getFlowToNorthMoney();
        insertNetFlowDetail(StaticValue.STOCK_MONEY_TO_NORTH,resNorth);

        List<Map<String, String>> resSourth = getFlowToSouthMoney();
        insertNetFlowDetail(StaticValue.STOCK_MONEY_TO_SOUTH,resSourth);

    }

}
