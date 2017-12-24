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

import javax.servlet.http.HttpServletRequest;
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
    public String add(Model model, String barcode) {
        Page<Category> categoriePage = categoryService.findAll();
        Page<Supplier> suppliersPage = supplierService.findAll();


        Goods goods = new Goods();
        goods.setBarcode(barcode);
        model.addAttribute("goods", goods);
        model.addAttribute("categories", categoriePage.getContent());
        model.addAttribute("suppliers", suppliersPage.getContent());
        return "goods/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Goods goods) {
        goodsService.add(goods);
        return "redirect:index?id=" + goods.getId();

    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(Model model, Long id, String barcode) {

        Goods goods = id == null ? goodsService.findByBarcode(barcode) : goodsService.findOne(id);

        Page<Category> categoriePage = categoryService.findAll();
        Page<Supplier> suppliersPage = supplierService.findAll();


        model.addAttribute("title", "编辑产品");
        model.addAttribute("goods", goods);
        model.addAttribute("categories", categoriePage.getContent());
        model.addAttribute("suppliers", suppliersPage.getContent());
        return "goods/add";
    }


    /**
     * 查询单个商品
     *
     * @return
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
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "1") Integer pageNo) {
        Page<Goods> page = goodsService.find(new PageRequest(pageNo - 1, 10));


        model.addAttribute("list", page.getContent());
        model.addAttribute("totalNum", page.getTotalElements());
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("pageNo", page.getNumber() + 1);

        return "goods/list";
    }

    /**
     * 删掉单个商品
     *
     * @return
     */
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        goodsService.delete(id);
        return "redirect:../list";
    }
}
