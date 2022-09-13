package com.gupiao.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gupiao.enums.ApiUrlPath;
import com.gupiao.generator.domain.IndustryTransactions;
import com.gupiao.generator.domain.StockMarketData;
import com.gupiao.service.HttpService;
import com.gupiao.util.BeanTransformation;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args) {

        //[{"日期":"2022-09-01","开盘":22.19,"收盘":22.38,"最高":22.54,"最低":21.91,"成交量":18135,"成交额":28917133.3,"振幅":2.85,"涨跌幅":1.36,"涨跌额":0.3,"换手率":2.21}]
        Map<String, String> pa = new HashMap<>();
        pa.put("CODE_ID", "002921");
        pa.put("START_DATE", "20220901");
        pa.put("END_DATE", "20220930");
        String res = null;
        try {
            res = HttpService.getDataFromUrl(ApiUrlPath.STOCK_ZH_A_HIST, pa);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Map<String,String>> resList = new Gson().fromJson(res,new TypeToken<List<Map<String, String>>>() {}.getType());
        for (Map<String,String> m:resList) {
            StockMarketData marketData = BeanTransformation.createStockMarketDataFromList(m);
            System.out.println(marketData);
        }
        System.out.println(res);
    }

}
