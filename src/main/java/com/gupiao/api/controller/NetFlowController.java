package com.gupiao.api.controller;


import com.google.gson.Gson;
import com.gupiao.api.requestParameters.NetFlowParameter;
import com.gupiao.api.requestParameters.StockParameter;
import com.gupiao.api.response.ResponseBean;
import com.gupiao.generator.domain.StockDetail;
import com.gupiao.generator.domain.StockMoneyNetFlow;
import com.gupiao.generator.mapper.StockDetailMapper;
import com.gupiao.generator.mapper.StockMoneyNetFlowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/netFlow" ,method = {RequestMethod.POST,RequestMethod.GET})
public class NetFlowController {

    @Autowired
    StockMoneyNetFlowMapper stockMoneyNetFlowMapper;

    /**
     * 获取历史记录
     * @param netFlowParameter
     * @return
     */
    @RequestMapping(value="/history/detail",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Object detail(@RequestBody NetFlowParameter netFlowParameter) {

        System.out.println(new Gson().toJson(netFlowParameter));

        List<StockMoneyNetFlow> stockMoneyNetFlowsList = stockMoneyNetFlowMapper.selectByDateAndDateRange(
                netFlowParameter.getFlowType(),
                netFlowParameter.getFlowStartDate(),
                netFlowParameter.getFlowEndDate());

        ResponseBean bean = new ResponseBean();
        bean.setStatus(Boolean.TRUE);
        bean.setMsg(stockMoneyNetFlowsList);

        return new Gson().toJson(bean);

    }

}
