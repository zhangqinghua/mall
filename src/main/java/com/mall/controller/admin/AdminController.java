package com.mall.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {

    @RequestMapping({"/", "/index"})
    public String index() {
        return "admin/index";
    }

    @RequestMapping("main")
    public String main() {
        return "admin/main";
    }

}
