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
import com.gupiao.service.StockService;
import com.gupiao.util.BeanTransformation;
import com.gupiao.util.StaticValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Action;
import java.io.IOException;
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

        Map<String, String> pa = new HashMap<>();
        pa.put("DATE_PARAMS", "202201");
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
        return "ok:";
    }

    @RequestMapping("/test3")
    @ResponseBody
    public String index3() {

        /*
        SysSetting setting = sysSettingMapper.selectByCode(StaticValue.UPDATE_STOCK_DATA_KEY_THREAD_SIZE);
        if(null == setting){
            setting = new SysSetting();
            setting.setSysKey(StaticValue.UPDATE_STOCK_DATA_KEY_THREAD_SIZE);
            setting.setSysValue("1");
            setting.setDes("");
            sysSettingMapper.insert(setting);
        }

        stockService.getAllStockYesterdayData(Integer.parseInt(setting.getSysValue()));
         */
        return "ok";
    }

}
