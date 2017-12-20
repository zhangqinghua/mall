package com.mall.controller;

import com.mall.entity.Category;
import com.mall.entity.Goods;
import com.mall.entity.Supplier;
import com.mall.service.CategoryService;
import com.mall.service.GoodsService;
import com.mall.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private Environment environment;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        Page<Category> categoriePage = categoryService.findAll();
        Page<Supplier> suppliersPage = supplierService.findAll();


        model.addAttribute("categories", categoriePage.getContent());
        model.addAttribute("suppliers", suppliersPage.getContent());
        return "goods/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Goods goods) {
        System.out.println(goods.toString());
        goodsService.add(goods);
        return "redirect:index?id=" + goods.getId();
    }


    /**
     * 查询单个商品
     *
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model, Long id, String barcode) {
        Goods goods = id == null ? goodsService.findByBarcode(barcode) : goodsService.findOne(id);


        // 产品分类列表，电子产品-手机-苹果手机
        List<Category> categories = new ArrayList<>();
        Category category = goods.getCategory();
        while (category != null) {
            categories.add(0, category);
            category = category.getParent();
        }

        List<String> imgs = new ArrayList<>();
        for (String img :  goods.getImg().split(",")) {
            imgs.add(environment.getProperty("file_url") + img);
        }

        model.addAttribute("imgs", imgs);
        model.addAttribute("categories", categories);
        model.addAttribute("goods", goods);

        return "goods/index";
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
