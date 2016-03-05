package com.rongyi.rpb.web.controller.v5;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rongyi.core.common.util.DateUtil;
import com.rongyi.easy.rpb.domain.Users;
import com.rongyi.rpb.web.controller.BaseController;

@Controller
@RequestMapping("/main")
public class MainController extends BaseController {

	private static final Logger LOGGER = Logger.getLogger(MainController.class);

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap model, HttpServletRequest request){
		LOGGER.info(request.getAttribute("X-Real-IP") + "," + request.getRemoteAddr() + ">>>index");
		try {
			Users user = getUser();
			request.getSession().setAttribute("USER_SESSIONKEY_", user);
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return "index";
	}

	/**
	 * 首页，今日重点
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		return "main";
	}

	/**
	 * 左侧
	 * 
	 * @param model
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public String left() {
		return "left";

	}

	/**
	 * 顶部
	 * 
	 * @param model
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String top(ModelMap model) {
		Users user = getUser();
		if (user != null) {
			model.put("name", user.getName());
			model.put("curdate",DateUtil.dateToString(new Date(), "yyyy年MM月dd日"));
		}
		return "top";
	}

	private Users getUser() {
		Users user = new Users();
		user.setAdmin(true);
		user.setName("admin");
		user.setPassword("admin");
		return user;
	}

}
