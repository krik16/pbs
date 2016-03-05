package com.rongyi.rpb.web.controller.v5;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rongyi.rpb.web.controller.BaseController;





/**
 * @author ShaoYanbin
 * @用户登录和注销
 * @author LiuChao 2014/9/25 14:03
 * */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@RequestMapping(value = "/userIndex", method = RequestMethod.GET)
	public String printWelcome(ModelMap model, Principal principal) {

		String name = principal.getName();
		model.addAttribute("name", name);
		return "adzones/index";

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model, String error, Authentication authentication) {
		model.addAttribute("error", error);
		return "index";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {

		return "login";

	}

}
