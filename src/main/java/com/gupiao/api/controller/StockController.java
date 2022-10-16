package com.gupiao.api.controller;

import com.google.gson.Gson;
import com.gupiao.api.response.ResponseBean;
import com.gupiao.bean.ComputeDailyBean;
import com.gupiao.generator.domain.StockDetail;
import com.gupiao.generator.mapper.StockDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/query")
public class StockController {

    @Autowired
    StockDetailMapper stockDetailMapper;

    @RequestMapping(value="/stock",method = RequestMethod.GET)
    @ResponseBody
    public Object test(@RequestParam("param") String param,
                       @RequestParam("pages") Integer pages) {

        //处理分页问题
        pages = pages*50;

        List<StockDetail> res = stockDetailMapper.selectByCodeAndPages(param,pages);
        return new Gson().toJson(res);

    }

    @RequestMapping(value="/stock/detail",method = RequestMethod.GET)
    @ResponseBody
    public Object detail(@RequestParam("code") String code) {

        StockDetail detail = stockDetailMapper.selectByCode(code);

        ResponseBean bean = new ResponseBean();
        bean.setStatus(Boolean.TRUE);
        bean.setMsg(detail);
        return new Gson().toJson(bean);

    }

    @RequestMapping(value="/stock/nextDetail",method = RequestMethod.GET)
    @ResponseBody
    public Object nextDetail(@RequestParam("code") String code) {

        StockDetail detail = stockDetailMapper.selectNextByCode(code);

        ResponseBean bean = new ResponseBean();
        bean.setStatus(Boolean.TRUE);
        bean.setMsg(detail);

        return new Gson().toJson(bean);

    }

    @RequestMapping(value="/stock/isFocus",method = RequestMethod.GET)
    @ResponseBody
    public Object isFocus(@RequestParam("code") String code,
                          @RequestParam("isFocus") Integer isFocus) {

        stockDetailMapper.updateFocusByCode(code,Integer.valueOf(isFocus));

        ResponseBean bean = new ResponseBean();
        bean.setStatus(Boolean.TRUE);
        bean.setMsg("success");

        return new Gson().toJson(bean);

    }
}
