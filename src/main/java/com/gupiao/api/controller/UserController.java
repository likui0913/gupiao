package com.gupiao.api.controller;

import com.google.gson.Gson;
import com.gupiao.aop.WebLoadAop;
import com.gupiao.api.response.ResponseBean;
import com.gupiao.generator.domain.SysUser;
import com.gupiao.generator.mapper.SysUserMapper;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    SysUserMapper sysUserMapper;

    @RequestMapping("/load")
    @ResponseBody
    public String load(@RequestParam("userName") String userName,
                       @RequestParam("passwd") String passwd,
                       HttpServletRequest request) {

        ResponseBean bean = new ResponseBean();

        SysUser sysUser = sysUserMapper.selectByUserName(userName);

        if(null != sysUser && sysUser.getPasswd().equals(passwd)){
            bean.setStatus(Boolean.TRUE);
            WebLoadAop.cache.put(request.getSession().getId(),System.currentTimeMillis());
        }else{
            bean.setStatus(Boolean.FALSE);
        }
        return new Gson().toJson(bean);
    }



}
