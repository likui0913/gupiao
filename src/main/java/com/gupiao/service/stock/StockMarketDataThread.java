package com.gupiao.service.stock;

import com.gupiao.bean.api.StockCode;
import com.gupiao.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StockMarketDataThread extends Thread{

    protected List<StockCode> lists;

    StockService stockService;

    public List<StockCode> getLists() {
        return lists;
    }

    public void setLists(List<StockCode> lists) {
        this.lists = lists;
    }

    public StockService getStockService() {
        return stockService;
    }

    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    @Override
    public void run() {
        for (StockCode code:lists) {
            stockService.getStockMarketAllDataByCode(code.getCode());
        }
    }

}
