package com.shouyingbao.pbs.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * kejun
 * 2016/3/14 16:21
 **/

@Controller
@RequestMapping("/main")
public class MainCommonController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(MainCommonController.class);


    @RequestMapping("/index")
    public String index(){
        LOGGER.info("index");
        return "index";
    }

    /**
     * 左侧
     */
    @RequestMapping(value = "/left", method = RequestMethod.GET)
    public String left() {
        LOGGER.info(">>>left");
        return "left";
    }

    /**
     * 顶部
     */
    @RequestMapping(value = "/top", method = RequestMethod.GET)
    public String top() {
        LOGGER.info(">>>top");
        return "top";
    }

}
