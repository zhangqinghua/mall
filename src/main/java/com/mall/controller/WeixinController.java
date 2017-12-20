package com.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("weixin")
public class WeixinController {

    @RequestMapping("scanBarcode")
    private String scan() {
        return "weixin/scanBarcode";
    }
}
