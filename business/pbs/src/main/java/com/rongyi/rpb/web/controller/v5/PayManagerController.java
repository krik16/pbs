package com.rongyi.rpb.web.controller.v5;

import com.rongyi.core.common.util.JsonUtil;
import com.rongyi.easy.rpb.vo.PaymentEntityVO;
import com.rongyi.rpb.constants.Constants;
import com.rongyi.rpb.service.PaymentService;
import com.rongyi.rpb.web.controller.BaseController;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/payManager")
public class PayManagerController extends BaseController {

	private static final Logger LOGGER = Logger.getLogger(PayManagerController.class);

	@Autowired
	PaymentService paymentService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap modelMap, String shopId, String currpage) {
		LOGGER.info(">>>payment index");
		modelMap.addAttribute("orderNum", "");
		modelMap.addAttribute("currpage", currpage);
		return "payManager/info";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap modelMap, String paramsJson) {
		LOGGER.info(">>>payment list");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			LOGGER.error(e1);
		}
		Map<String, Object> paramsMap;
		if (paramsJson == null) {
			resultMap.put("msg", "参数为NULL，请关闭再重试！");
			resultMap.put("status", 0);
			return null;
		}
		paramsMap = JsonUtil.getMapFromJson(paramsJson);
		String currpage = (String) paramsMap.get("currpage");
		String orderNum = (String) paramsMap.get("orderNum");
		String payNo = (String) paramsMap.get("payNo");
		String status = (String) paramsMap.get("status");
		String tradeType = (String) paramsMap.get("tradeType");
		List<PaymentEntityVO> list = paymentService.selectPageListBySearch(paramsMap);
		Long pageTotle = paymentService.selectPageListCountBySearch(paramsMap);
		Integer rowContNum = (int) Math.ceil(pageTotle / Constants.PAGE.pageSize);
		modelMap.addAttribute("rowCont", rowContNum);
		modelMap.addAttribute("currpage", currpage);
		modelMap.addAttribute("orderNum", orderNum);
		modelMap.addAttribute("payNo", payNo);
		modelMap.addAttribute("status", status);
		modelMap.addAttribute("tradeType", tradeType);
		modelMap.addAttribute("paymentList", list);
		return "payManager/payManager_search_ajaxlist";
	}

}
