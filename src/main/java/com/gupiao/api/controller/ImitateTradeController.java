package com.gupiao.api.controller;

import com.google.gson.Gson;
import com.gupiao.api.requestParameters.BokeParameter;
import com.gupiao.api.requestParameters.ImitateTradeParameters;
import com.gupiao.api.response.ResponseBean;
import com.gupiao.generator.domain.BokeDetail;
import com.gupiao.generator.mapper.BokeDetailMapper;
import com.gupiao.service.ImitateTradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/imitate")
public class ImitateTradeController {

    @Autowired
    ImitateTradeService imitateTradeService;

    /**
     * 新增一个模拟信息
     * @param imitateTradeParameters
     * @return
     */
    @RequestMapping(value="/insert",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Object insert(@RequestBody ImitateTradeParameters imitateTradeParameters) {

        try{
            Boolean res = imitateTradeService.insertImitateTradeByCode(imitateTradeParameters.getCode());
        }catch (Exception e){
            log.error("ImitateTradeController--insert error",e);
            return new Gson().toJson("false");
        }

        return new Gson().toJson("true");

    }

    /**
     * 获取历史记录
     * @param imitateTradeParameters
     * @return
     */
    @RequestMapping(value="/update",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Object update(@RequestBody ImitateTradeParameters imitateTradeParameters) {

        try{
            Boolean res = imitateTradeService.updateImitateTradeByCode(imitateTradeParameters.getId());
        }catch (Exception e){
            log.error("ImitateTradeController--update error",e);
            return new Gson().toJson("false");
        }

        return new Gson().toJson("true");

    }

}
