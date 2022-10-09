package com.gupiao.api.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gupiao.enums.ApiUrlPath;
import com.gupiao.generator.domain.IndustryTransactions;
import com.gupiao.generator.domain.StockDetail;
import com.gupiao.generator.domain.StockMarketData;
import com.gupiao.generator.domain.SysSetting;
import com.gupiao.generator.mapper.IndustryTransactionsMapper;
import com.gupiao.generator.mapper.StockDetailMapper;
import com.gupiao.generator.mapper.StockMarketDataMapper;
import com.gupiao.generator.mapper.SysSettingMapper;
import com.gupiao.service.HttpService;
import com.gupiao.service.StatisticService;
import com.gupiao.service.StockService;
import com.gupiao.util.BeanTransformation;
import com.gupiao.util.StaticValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Action;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api")
public class Test {

    @Autowired
    StockDetailMapper stockDetailMapper;

    @Autowired
    IndustryTransactionsMapper industryTransactionsMapper;

    @Autowired
    StockService stockService;

    @Autowired
    StockMarketDataMapper stockMarketDataMapper;

    @Autowired
    SysSettingMapper sysSettingMapper;

    @Autowired
    StatisticService statisticService;

    @RequestMapping("/test")
    @ResponseBody
    public String index() {

        Integer count = 0;
        List<StockDetail> stockDetailList = stockService.updateAllStockMsg(100);
        for (StockDetail detail: stockDetailList) {
            count ++;
        }
        return "ok:" + count.toString();
    }

    @RequestMapping("/test2")
    @ResponseBody
    public String index2() {

        stockService.updateAllStockMsg(1);
        return "ok:";
    }

    @RequestMapping("/test3")
    @ResponseBody
    public String index3() {

        stockService.getAllStockYesterdayData(1,Boolean.TRUE);
        return "ok";
    }

    @RequestMapping("/test4")
    @ResponseBody
    public String index4(
            @RequestParam("code") String code,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        try{
            statisticService.cpmputeXDayMovingAverage(code,startDate,endDate);
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }

    }





}
