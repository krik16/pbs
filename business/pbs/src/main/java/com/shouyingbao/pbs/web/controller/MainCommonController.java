package com.shouyingbao.pbs.web.controller;

import com.shouyingbao.pbs.core.common.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Date;

/**
 * kejun
 * 2016/3/14 16:21
 **/

@Controller
@RequestMapping("/main")
public class MainCommonController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);


    @RequestMapping("/index")
    public String index(){
        LOGGER.info("index");
        return "index";
    }
    /**
     * 跳转到commonpage页面
     *
     * @return
     */
    @RequestMapping(value = "/common", method = RequestMethod.GET)
    public String getCommonPage() {
        LOGGER.debug("Received request to show common page");
        return "test";
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


    /**
     * 左侧
     *
     * @param model
     * @param principal
     * @return
     */
    @RequestMapping(value = "/left", method = RequestMethod.GET)
    public String left(ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
                       Principal principal) {
        try {
            LOGGER.info(">>>left");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return "left";
    }

    /**
     * 顶部
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/top", method = RequestMethod.GET)
    public String top(ModelMap model, String currentMallId, HttpServletRequest request, HttpServletResponse response,
                      HttpSession session) {
        try {
            LOGGER.info(">>>top");
            model.put("curdate", DateUtil.dateToString(new Date(), "yyyy年MM月dd日"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return "top";
    }

}
