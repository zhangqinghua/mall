package com.mall.controller;

import com.mall.entity.Category;
import com.mall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {


    @Autowired
    private CategoryService service;


    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        Page<Category> page = service.findAll();

        model.addAttribute("list", page.getContent());
        model.addAttribute("totalNum", page.getTotalElements());
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("pageNo", page.getNumber() + 1);

        return "category/index";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Model model, Category entity) {
        service.save(entity);
        return "forward:index";
    }


    @RequestMapping("/delete")
    public String delete(Model model, Long id) {
        service.delete(id);
        return "forward:index";
    }

}
