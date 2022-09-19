package com.gupiao.service.thread;

import com.gupiao.bean.api.StockCode;
import com.gupiao.service.StockService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class StockMarketDataIncrementalThread extends Thread{

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
        try{
            for (StockCode code:lists) {
                try{
                    stockService.getStockYesterdayDataByCode(code.getCode());
                }catch (Exception e){
                    log.error("StockMarketDataIncrementalThread 出错！",e);
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            log.error("StockMarketDataIncrementalThread 出错！",e);
            e.printStackTrace();
        }

    }

}
