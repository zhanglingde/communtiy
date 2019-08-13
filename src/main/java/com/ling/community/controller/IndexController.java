package com.ling.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 把这个类作为路由API
 */
@Controller
public class IndexController {
    @GetMapping("/")
    public String index(){

        return "index";
    }
}
