package com.jnshu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @RequestMapping(value = "/testInterceptor")
    public String testInterceptor(){
        System.out.println("testInterceptor执行了。。。");
        return "a";
    }
}
