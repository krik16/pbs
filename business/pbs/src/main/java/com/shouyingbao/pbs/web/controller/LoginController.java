package com.shouyingbao.pbs.web.controller;

import com.shouyingbao.pbs.constants.ConstantEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * kejun
 * 2016/3/14 16:22
 **/
@Controller
@RequestMapping("/auth")
public class LoginController{
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    /**
     * 指向登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(
            @RequestParam(value = "error", required = false) boolean error,
            ModelMap model,HttpServletRequest request) {
        if (error == true) {
            model.put("error", ConstantEnum.ERROR_LOGIN.getValueStr());
        } else {
            model.put("error", "");
        }
        return "login";

    }

    /**
     * 指定无访问额权限页面
     *
     * @return
     */
    @RequestMapping(value = "/denied", method = RequestMethod.GET)
    public String test() {
        LOGGER.debug("Received request to show denied page");
        return "denied";

    }
    /**
     * 指定无访问额权限页面
     *
     * @return
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String getDeniedPage() {
        LOGGER.debug("test");
        return "denied";

    }
}
