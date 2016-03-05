package com.rongyi.rpb.web.controller.v5;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rongyi.rpb.service.UnionpayMarketingService;
import com.rongyi.rpb.web.controller.BaseController;
/**
 * 
 * @author wangzhiwen
 *
 */
@Controller
@RequestMapping(value = "/union/unionMarketing")
public class UnionMarketingController extends BaseController {
	protected static final Logger LOGGER = Logger.getLogger(UnionMarketingController.class);
	
	@RequestMapping("/index")
	public String index() {
		return "union/unionActivityRegister";
	}
	@RequestMapping("/index2")
	public String index2() {
		return "union/deleteUnionActivity";
	}
	@Autowired
	private UnionpayMarketingService unionpayMarketingService;
	/**
	 * @Description: 创建活动
	 * @return
	 * @throws ParseException 
	 * @Author: wangzhiwen
	 * @datetime:2015年7月3日下午17:36:31
	 **/
	@SuppressWarnings("unchecked")
	@RequestMapping("/union/unionpayMarketing")
	public void validateCoupon(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			LOGGER.error(e1);
		}
		Map<String, String[]> paramMap = request.getParameterMap();
		unionpayMarketingService.validateXML(paramMap);
	}
	
	/**
	 * @Description: 创建活动
	 * @return
	 * @throws ParseException 
	 * @Author: wangzhiwen
	 * @datetime:2015年7月3日下午17:36:31
	 **/
	@SuppressWarnings("unchecked")
	@RequestMapping("/union/deleteUnionActivity")
	public void deleteByEventStatusController(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			LOGGER.error(e1);
		}
		Map<String, String[]> paramMap = request.getParameterMap();
		unionpayMarketingService.deleteByEventStatus(paramMap);
	}
}
