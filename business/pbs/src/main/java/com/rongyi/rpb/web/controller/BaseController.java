package com.rongyi.rpb.web.controller;

import com.rongyi.rpb.common.JsonResult;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 类BaseController.java的实现描述
 * 
 * @author jiejie 2014年6月19日 下午1:40:49
 */
public class BaseController {
	private static final Logger LOGGER = Logger.getLogger(BaseController.class);
	protected static final Integer PAGE_SIZE = 10;

	/**
	 * 返回json数据
	 * 
	 * @return
	 */
	protected <T> void writeJson(T t, String msg, boolean isSuccess, Integer totalPage, HttpServletResponse response) {
		JsonResult<T> result = null;
		if (isSuccess) {
			result = JsonResult.successResult(t, msg, totalPage);
		} else {
			result = JsonResult.failResult(t, msg, totalPage);
		}
		JSONObject json = JSONObject.fromObject(result);
		response.setContentType("application/json;charset=UTF-8");
		LOGGER.info(json.toString());
		try {
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}

	/**
	 * 返回totalPage
	 */
	protected Integer getTotalPage(Integer totalNum) {
		return (totalNum % PAGE_SIZE == 0 ? totalNum / PAGE_SIZE : totalNum / PAGE_SIZE + 1);
	}

	/**
	 * 返回totalPage
	 */
	protected Integer getTotalPage(Integer totalNum, int pageSize) {
		return (totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1);
	}

	protected Integer getStartRecordIndex(Integer nowPageIndex) {
		return nowPageIndex * PAGE_SIZE;
	}

	/**
	 * 直接输出json
	 */
	public void responseJson(HttpServletResponse response, String json) {
		doPrint(response, json, "application/json;charset=UTF-8");
	}

	/**
	 * 向客户端输出指定格式的文档
	 * 
	 * @param response
	 *            响应
	 * @param text
	 *            要发的内容
	 * @param contentType
	 *            发送的格式
	 */
	public void doPrint(HttpServletResponse response, String text, String contentType) {
		try {
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}


}
