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

        Map<String, String> pa = new HashMap<>();
        pa.put("CODE_ID", "002119");
        String res = null;
        try {
            res = HttpService.getDataFromUrl(ApiUrlPath.STOCK_ZH_A_HIST_ALL, pa);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Map<String, String>> resList = new Gson().fromJson(res, new TypeToken<List<Map<String, String>>>() {}.getType());
        for (Map<String,String> map: resList) {
            StockMarketData b = BeanTransformation.createStockMarketDataFromList(map);
            System.out.println(b);
            //industryTransactionsMapper.insert(b);
        }

    }

}
