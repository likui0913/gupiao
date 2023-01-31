package com.gupiao.api.controller;

import com.google.gson.Gson;
import com.gupiao.api.requestParameters.BokeParameter;
import com.gupiao.api.requestParameters.NetFlowParameter;
import com.gupiao.api.response.ResponseBean;
import com.gupiao.generator.domain.BokeDetail;
import com.gupiao.generator.domain.StockMoneyNetFlow;
import com.gupiao.generator.mapper.BokeDetailMapper;
import com.gupiao.service.TransactionPlaybackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/boke")
public class BokeController {

    @Autowired
    BokeDetailMapper bokeDetailMapper;


    /**
     * 获取历史记录
     * @param bokeParameter
     * @return
     */
    @RequestMapping(value="/add",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Object add(@RequestBody BokeParameter bokeParameter) {

        BokeDetail bokeDetail = new BokeDetail();
        bokeDetail.setTitleId(bokeParameter.getTitleId());
        bokeDetail.setTitleName(bokeParameter.getTitleName());
        bokeDetail.setTitleType(bokeParameter.getTitleType());
        //处理内容
        String msg = bokeParameter.getTitleDetail().replace(" ","");
        while (true){
            String msgTmp = msg.replace("\n\n","\n");
            if(msgTmp.length() == msg.length()){
                msg = msgTmp;
                break;
            }else{
                msg = msgTmp;
            }
        }

        bokeDetail.setTitleDetail(msg);
        bokeDetail.setDes(bokeParameter.getDes());

        bokeDetailMapper.insert(bokeDetail);

        ResponseBean bean = new ResponseBean();
        bean.setStatus(Boolean.TRUE);
        bean.setMsg(bokeDetail.getId());

        return new Gson().toJson(bean);

    }

    /**
     * 获取历史记录
     * @param bokeParameter
     * @return
     */
    @RequestMapping(value="/getDetail",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Object getDetail(@RequestBody BokeParameter bokeParameter) {

        BokeDetail bokeDetail = bokeDetailMapper.selectByID(bokeParameter.getId());

        ResponseBean bean = new ResponseBean();
        bean.setStatus(Boolean.TRUE);
        bean.setMsg(bokeDetail);

        return new Gson().toJson(bean);

    }

    /**
     * 获取历史记录
     * @param bokeParameter
     * @return
     */
    @RequestMapping(value="/getNextDetail",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Object getNextDetail(@RequestBody BokeParameter bokeParameter) {

        BokeDetail bokeDetail = bokeDetailMapper.selectNextByID(bokeParameter.getId());

        ResponseBean bean = new ResponseBean();
        bean.setStatus(Boolean.TRUE);
        bean.setMsg(bokeDetail);

        return new Gson().toJson(bean);

    }

}
