package com.gupiao.api.controller;

import com.google.gson.Gson;
import com.gupiao.api.response.ResponseBean;
import com.gupiao.bean.ComputeDailyBean;
import com.gupiao.generator.domain.StockDetail;
import com.gupiao.generator.mapper.StockDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
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
}
