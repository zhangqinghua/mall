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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
        List<Supplier> suppliers = supplierService.findAll();

        Goods goods = new Goods();
        model.addAttribute("goods", goods);
        model.addAttribute("categories", categoriePage.getContent());
        model.addAttribute("suppliers", suppliers);
        return "goods/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Goods goods) {
        goodsService.add(goods);
        return "redirect:index?id=" + goods.getId();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(Model model, @PathVariable Long id) {
        Goods goods = goodsService.findOne(id);

        // 不存在此产品，则跳转到新增页面
        if (goods == null) {
            return "forward:add";
        }

        Page<Category> categoriePage = categoryService.findAll();
        List<Supplier> suppliers = supplierService.findAll();

        model.addAttribute("title", "编辑产品");
        model.addAttribute("goods", goods);
        model.addAttribute("categories", categoriePage.getContent());
        model.addAttribute("suppliers", suppliers);
        return "goods/add";
    }


    /**
     * 查询单个商品
     *
     * @return 产品详细页面
     */
    @RequestMapping("/index")
    public String index(Model model, Long id, String barcode) {
        Goods goods = id == null ? goodsService.findByBarcode(barcode) : goodsService.findOne(id);

        // 如果不存在此产品，则跳转到新建页面
        if (goods == null) {
            return "redirect:add?barcode=" + barcode;
        }


        // 产品分类列表，电子产品-手机-苹果手机
        List<Category> categories = new ArrayList<>();
        Category category = goods.getCategory();
        while (category != null) {
            categories.add(0, category);
            category = category.getParent();
        }

        List<String> imgs = new ArrayList<>();
        for (String img : goods.getImg().split(",")) {
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
     * @return 产品列表
     */
    @RequestMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "1") Integer pageNo) {
        Page<Goods> page = goodsService.find(new PageRequest(pageNo - 1, 10));
        List<Supplier> suppliers = supplierService.findAll();

        model.addAttribute("suppliers", suppliers);
        model.addAttribute("list", page.getContent());
        model.addAttribute("totalNum", page.getTotalElements());
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("pageNo", page.getNumber() + 1);

        return "goods/list";
    }

    /**
     * 删掉单个商品
     *
     * @return 产品列表
     */
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        goodsService.delete(id);
        return "redirect:../list";
    }

    /**
     * 微信扫条形码，如果不存在此产品新跳转到简单添加页面
     *
     * @param model   Springboot页面模型
     * @param barcode 条形码
     * @return 新增产品页面
     */
    @RequestMapping(value = "/weixin_add", method = RequestMethod.GET)
    public String weixin_add(Model model, String barcode) {
        model.addAttribute("barcode", barcode);
        return "goods/weixin_add";
    }

    /**
     * 添加新产品简短表单提交后，跳转到扫一扫页面继续添加
     *
     * @param goods 产品实体
     * @return 微信扫一扫页面
     */
    @RequestMapping(value = "/weixin_add", method = RequestMethod.POST)
    public String weixin_add(Goods goods) {
        goodsService.add(goods);
        return "redirect:/weixin/scan";
    }


    /**
     * 微信扫条形码，如果已经存在此产品了，则展示
     *
     * @return 展示页面
     */
    @RequestMapping("/weixin_show")
    public String weixin_show(Model model, String barcode) {
        Goods goods = goodsService.findByBarcode(barcode);
        if (goods == null) {
            return weixin_add(model, barcode);
        }
        model.addAttribute("goods", goods);
        return "goods/weixin_show";
    }


}