package com.gupiao.api.controller;

import com.gupiao.generator.domain.StockDetail;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @RequestMapping("/load")
    @ResponseBody
    public String load() {
        return "ok";
    }



}
