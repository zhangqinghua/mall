package com.mall.controller;

import com.mall.entity.Goods;
import com.mall.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/test")
    public String test() {
        return "index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "goods/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Goods goods) {
        System.out.println(goods.toString());
        goodsService.add(goods);
        return null;
    }


    /**
     * 查询单个商品
     *
     * @return
     */
    public String one() {
        return null;
    }

    /**
     * 查询多个商品
     *
     * @return
     */
    public String list() {
        return null;
    }

    /**
     * 删掉单个商品
     *
     * @return
     */
    public String delete() {
        return null;
    }
}
