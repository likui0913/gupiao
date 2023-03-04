package com.gupiao.api.controller;

import com.google.gson.Gson;
import com.gupiao.api.requestParameters.ComputeParameter;
import com.gupiao.api.requestParameters.StaticNDayParameter;
import com.gupiao.api.response.ResponseBean;
import com.gupiao.bean.ComputeDailyBean;
import com.gupiao.generator.domain.StockMarketStaticData;
import com.gupiao.generator.mapper.StockMarketStaticDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping(value = "/static")
public class StaticController {

    @Autowired
    StockMarketStaticDataMapper stockMarketStaticDataMapper;

    @RequestMapping(value="/NDayData",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Object queryNDayStatic(
            @RequestBody StaticNDayParameter staticNDayParameter) {

        String trendSort = null;
        if(null == staticNDayParameter.getTrendType() || "".equals(staticNDayParameter.getTrendType())){
            //未设置排序种类则直接不排序
            trendSort = null;
        }else{
            if(null == staticNDayParameter.getTrendSort() || "".equals(staticNDayParameter.getTrendSort())){
                trendSort = null;
            }else if(staticNDayParameter.getTrendSort().toLowerCase().equals("asc")){
                trendSort = staticNDayParameter.getTrendType() + " asc";
            }else {
                trendSort = staticNDayParameter.getTrendType() + " desc";
            }
        }
        List<StockMarketStaticData> res = stockMarketStaticDataMapper.selectByParameter(
                staticNDayParameter.getDays(),
                staticNDayParameter.getDate(),
                staticNDayParameter.getStartPrice(),
                staticNDayParameter.getEndPrice(),
                staticNDayParameter.getShowRows(),
                trendSort,
                staticNDayParameter.getPriceDiffSort(),
                staticNDayParameter.getTurnoverAvgSort());

        ResponseBean bean = new ResponseBean();
        bean.setStatus(Boolean.TRUE);
        bean.setMsg(res);
        return new Gson().toJson(bean);
    }

}
