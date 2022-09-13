package com.gupiao.service.stock;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gupiao.bean.api.StockCode;
import com.gupiao.bean.api.StockMsg;
import com.gupiao.enums.ApiUrlPath;
import com.gupiao.generator.domain.StockDetail;
import com.gupiao.generator.mapper.StockDetailMapper;
import com.gupiao.service.HttpService;
import com.gupiao.util.BeanTransformation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class StockServiceThread extends Thread{

    public List<StockCode> getLists() {
        return lists;
    }

    public void setLists(List<StockCode> lists) {
        this.lists = lists;
    }

    protected List<StockCode> lists;

    public StockDetailMapper getStockDetailMapper() {
        return stockDetailMapper;
    }

    public void setStockDetailMapper(StockDetailMapper stockDetailMapper) {
        this.stockDetailMapper = stockDetailMapper;
    }

    @Autowired
    StockDetailMapper stockDetailMapper;

    @Override
    public void run() {

        if( null == lists ){
            return;
        }

        try {
            for ( StockCode code : lists ) {

                //log.info("线程ID:" + Thread.currentThread().getId() + " 处理Code:" + code.getCode());
                StockDetail detail = new StockDetail();

                //0.检查股票是否已经存在
                StockDetail detailTmp = stockDetailMapper.selectByCode(code.getCode());
                if(null != detailTmp && null != detailTmp.getStockCode()){
                    //log.info("线程ID:" + Thread.currentThread().getId() + " 股票已经存在，Code:" + code.getCode());
                    continue;
                }

                //1.获取股票详细信息
                HashMap map = new HashMap();
                map.put("CODE_ID",code.getCode());
                String res = HttpService.getDataFromUrl(ApiUrlPath.STOCK_INDIVIDUAL_INFO_EM,map);
                List<StockMsg> r = new Gson().fromJson(res,new TypeToken<List<StockMsg>>() {}.getType());

                //2.解析查询结果
                detail = BeanTransformation.createStockDetailFromList(r);

                stockDetailMapper.insert(detail);
                Thread.sleep(100);
            }
        } catch (IOException e) {
            log.error("StockServiceThread--run出错", e);
        } catch (InterruptedException e) {
            log.error("StockServiceThread--run出错", e);
        }
    }

}
