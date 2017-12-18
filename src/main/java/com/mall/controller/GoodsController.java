package com.mall.controller;

import com.mall.entity.Category;
import com.mall.entity.Goods;
import com.mall.entity.Supplier;
import com.mall.service.CategoryService;
import com.mall.service.GoodsService;
import com.mall.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SupplierService supplierService;

    @RequestMapping("/test")
    public String test() {
        return "index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        Page<Category> categoriePage = categoryService.findAll();
        Page<Supplier> suppliersPage = supplierService.findAll();

        model.addAttribute("categories", categoriePage.getContent());
        model.addAttribute("suppliers", suppliersPage.getContent());
        return "goods/add1";
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
