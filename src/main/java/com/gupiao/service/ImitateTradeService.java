package com.gupiao.service;

import com.google.gson.Gson;
import com.gupiao.generator.domain.ImitateTradeData;
import com.gupiao.generator.domain.StockDetail;
import com.gupiao.generator.domain.StockMarketRuntimeData;
import com.gupiao.generator.mapper.ImitateTradeDataMapper;
import com.gupiao.generator.mapper.StockDetailMapper;
import com.gupiao.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 模拟交易服务类
 */
@Component
@Slf4j
public class ImitateTradeService {

    @Autowired
    ImitateTradeDataMapper imitateTradeDataMapper;

    @Autowired
    StockDetailMapper stockDetailMapper;

    @Autowired
    UpdateStockDailySaleService updateStockDailySaleService;

    /**
     * 获取全部模拟信息
     * @return
     */
    public List<ImitateTradeData> getAllImitateTradeData(){
        return imitateTradeDataMapper.selectAll();
    }

    /**
     * 新增1个模拟信息
     * @param code
     * @return
     */
    public Boolean insertImitateTradeByCode(String code){

        StockDetail stockDetail = stockDetailMapper.selectByCode(code);
        if(null == stockDetail){
            throw new RuntimeException("code:"+ code + "不存在！！！");
        }

        List<StockMarketRuntimeData> resAllData = updateStockDailySaleService.getNowTradeData();
        StockMarketRuntimeData tmpDate = null;
        for (StockMarketRuntimeData d:resAllData) {
            if(d.getStockCode().equals(code)){
                tmpDate = d;
                break;
            }
        }

        if(null == tmpDate){
            throw new RuntimeException("未找到code:" + code + "对应的实时交易信息");
        }
        ImitateTradeData tmpImitateTradeData = new ImitateTradeData();
        tmpImitateTradeData.setUserName("lk");
        tmpImitateTradeData.setStockCode(code);
        tmpImitateTradeData.setTradeInPrice(tmpDate.getNewPrice());
        tmpImitateTradeData.setTradeInDate(DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE2));

        tmpImitateTradeData.setTradeOutDate("0000-00-00 00:00:00");
        tmpImitateTradeData.setTradeOutPrice(BigDecimal.ZERO);

        imitateTradeDataMapper.insert(tmpImitateTradeData);
        log.info("新增一个模拟信息:{}",new Gson().toJson(tmpImitateTradeData));
        return Boolean.TRUE;

    }

    /**
     * 结束一个模拟信息
     * @param id
     * @return
     */
    public Boolean updateImitateTradeByCode(Integer id){

        ImitateTradeData imitateTradeData = imitateTradeDataMapper.selectById(id);
        if(null == imitateTradeData){
            throw new RuntimeException("未找到对应id:" + id);
        }

        String code = imitateTradeData.getStockCode();

        List<StockMarketRuntimeData> resAllData = updateStockDailySaleService.getNowTradeData();
        StockMarketRuntimeData tmpDate = null;
        for (StockMarketRuntimeData d:resAllData) {
            if(d.getStockCode().equals(code)){
                tmpDate = d;
                break;
            }
        }

        if(null == tmpDate){
            throw new RuntimeException("未找到code:" + code + "对应的实时交易信息");
        }

        imitateTradeData.setTradeOutDate(DateUtils.converDateToString(new Date(),DateUtils.DATE_FORMATE2));
        imitateTradeData.setTradeOutPrice(tmpDate.getNewPrice());

        imitateTradeDataMapper.updateById(imitateTradeData);
        log.info("更新一个模拟信息:{}",new Gson().toJson(imitateTradeData));
        return Boolean.TRUE;

    }

}
