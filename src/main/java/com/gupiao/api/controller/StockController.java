package com.gupiao.api.controller;

import com.google.gson.Gson;
import com.gupiao.api.requestParameters.SaleDataParameter;
import com.gupiao.api.requestParameters.StockParameter;
import com.gupiao.api.response.ResponseBean;
import com.gupiao.generator.domain.StockDetail;
import com.gupiao.generator.mapper.StockDetailMapper;
import com.gupiao.generator.mapper.StockMarketDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/query" ,method = {RequestMethod.POST,RequestMethod.GET})
public class StockController {

    @Autowired
    StockDetailMapper stockDetailMapper;

    @Autowired
    StockMarketDataMapper stockMarketDataMapper;

    @RequestMapping(value="/stockDetailList",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Object stockDetailList(@RequestBody StockParameter stockParameter) {

        //处理分页问题
        Integer pages = Integer.parseInt(stockParameter.getPages())*50;

        List<StockDetail> res = stockDetailMapper.selectByCodeAndPages(
                stockParameter.getCode(),
                stockParameter.getIsFocus(),
                stockParameter.getIndustry(),
                pages);

        ResponseBean bean = new ResponseBean();
        bean.setStatus(Boolean.TRUE);
        bean.setMsg(res);

        return new Gson().toJson(bean);

    }

    @RequestMapping(value="/stock/detail",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Object detail(@RequestBody StockParameter stockParameter) {

        System.out.println(new Gson().toJson(stockParameter));

        StockDetail detail = stockDetailMapper.selectByCode(stockParameter.getCode());

        ResponseBean bean = new ResponseBean();
        bean.setStatus(Boolean.TRUE);
        bean.setMsg(detail);

        return new Gson().toJson(bean);

    }

    @RequestMapping(value="/stock/nextDetail",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Object nextDetail(@RequestBody StockParameter stockParameter) {

        StockDetail detail = stockDetailMapper.selectNextByCode(
                stockParameter.getCode(),
                stockParameter.getIsFocus(),
                stockParameter.getIndustry());

        ResponseBean bean = new ResponseBean();
        bean.setStatus(Boolean.TRUE);
        bean.setMsg(detail);

        return new Gson().toJson(bean);

    }

    @RequestMapping(value="/stock/lastDetail",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Object lastDetail(@RequestBody StockParameter stockParameter) {

        StockDetail detail = stockDetailMapper.selectLastByCode(
                stockParameter.getCode(),
                stockParameter.getIsFocus(),
                stockParameter.getIndustry());

        ResponseBean bean = new ResponseBean();
        bean.setStatus(Boolean.TRUE);
        bean.setMsg(detail);

        return new Gson().toJson(bean);

    }

    @RequestMapping(value="/stock/isFocus",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Object isFocus(@RequestParam("code") String code,
                          @RequestParam("isFocus") Integer isFocus) {

        stockDetailMapper.updateFocusByCode(code,Integer.valueOf(isFocus));

        ResponseBean bean = new ResponseBean();
        bean.setStatus(Boolean.TRUE);
        bean.setMsg("success");

        return new Gson().toJson(bean);

    }


    @RequestMapping(value="/stock/saleDataDetail",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Object saleDataDetail(@RequestBody SaleDataParameter stockParameter) {

        stockMarketDataMapper.selectByCodeAndTwoDate(
                stockParameter.getCode(),stockParameter.getStartDate(),stockParameter.getEndDate());

        ResponseBean bean = new ResponseBean();
        bean.setStatus(Boolean.TRUE);
        bean.setMsg("success");

        return new Gson().toJson(bean);

    }
}
