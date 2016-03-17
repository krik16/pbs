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

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);


    /**
     * 跳转到commonpage页面
     *
     * @return
     */
    @RequestMapping(value = "/common", method = RequestMethod.GET)
    public String getCommonPage() {
        LOGGER.debug("Received request to show common page");
        return "commonpage";
    }

    /**
     * 跳转到adminpage页面
     *
     * @return
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String getAadminPage() {
        LOGGER.debug("Received request to show admin page");
        return "adminpage";

    }
}
