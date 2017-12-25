package com.mall.controller;

import com.mall.entity.Supplier;
import com.mall.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;


    @RequestMapping({"/", "/index"})
    public String index(Model model, @RequestParam(defaultValue = "1") Integer pageNo) {
        Page<Supplier> page = supplierService.find(new PageRequest(pageNo - 1, 10));

        model.addAttribute("list", page.getContent());
        model.addAttribute("totalNum", page.getTotalElements());
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("pageNo", page.getNumber() + 1);

        return "supplier/index";
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public String save() {
        return "supplier/save";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Model model, Supplier supplier) {
        supplierService.save(supplier);
        return "forward:index";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(Model model, Long id) {
        supplierService.delete(id);
        return "forward:index";
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        Page<Supplier> page = supplierService.find(new PageRequest(1 - 1, 10));
        return page.getContent().get(0).getGoodsSuppliers().size() + "";
    }
}
