package com.gupiao.api.controller;

import com.google.gson.Gson;
import com.gupiao.api.response.ResponseBean;
import com.gupiao.bean.ComputeDailyBean;
import com.gupiao.service.TransactionPlaybackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/compute")
public class ComputeController {

    @Autowired
    TransactionPlaybackService transactionPlaybackService;

    @RequestMapping(value="/test",method = RequestMethod.GET)
    @ResponseBody
    public Object test(@RequestParam("code") String code,
                       @RequestParam("startDate") String startDate,
                       @RequestParam("endDate") String endDate) {
        List<ComputeDailyBean> res = transactionPlaybackService.computeTransactionPlayback(code,startDate,endDate);
        String resString = "";
        for(ComputeDailyBean bean:res){
            resString += "日期:"+bean.getDate() + ",指标:" + bean.getQuoteChange() + ",结果:" + bean.getTransactionResults() + "<br>";
        }

        ResponseBean bean = new ResponseBean();
        bean.setStatus(Boolean.TRUE);
        bean.setMsg(res);

        return new Gson().toJson(bean);

        /*
        Integer rise=0,decline=0,flat=0;
        for (ComputeDailyBean bean:res) {
            if(bean.getQuoteChange().contains("-")){
                decline++;
            }else{
                rise++;
            }
        }
        resString = resString + "上:" + rise + ",下:" + decline + "<br>";
         */

    }

    @RequestMapping(value="/test2",method = RequestMethod.GET)
    @ResponseBody
    public String test2(@RequestParam("code") String code,
                       @RequestParam("startDate") String startDate,
                       @RequestParam("days") String days) {
        List<ComputeDailyBean> res = transactionPlaybackService.computeTransactionPlaybackDays(code,startDate,days);

        String resString = "";

        for(ComputeDailyBean bean:res){
            resString += "日期:"+bean.getDate() + ",指标:" + bean.getQuoteChange() + ",结果:" + bean.getTransactionResults() + "<br>";
        }

        Integer rise=0,decline=0,flat=0;
        for (ComputeDailyBean bean:res) {
            if(bean.getQuoteChange().contains("-")){
                decline++;
            }else{
                rise++;
            }
        }
        resString = resString + "上:" + rise + ",下:" + decline + "<br>";

        return resString;
    }

}
