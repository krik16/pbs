package com.shouyingbao.pbs.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * kejun
 * 2016/3/8 17:33
 **/
@Controller
@RequestMapping("/bill")
public class PaymentbIllController {

    @RequestMapping("/test")
    public  String test(){
        System.err.println("test");
        return "login";
    }
}
