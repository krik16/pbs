package com.rongyi.rpb.nsynchronous;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.rongyi.easy.osm.entity.OrderFormEntity;
import com.rongyi.easy.rmmm.dto.user.UserAccountDto;
import com.rongyi.easy.rpb.domain.PaymentEntity;
import com.rongyi.rpb.constants.Constants;
import com.rongyi.rpb.service.PaymentService;
import com.rongyi.rss.mallshop.order.ROAOrderFormService;
import com.rongyi.rss.mallshop.user.ROAUserAccountService;

@Component
public class OrderFormNsyn {

	private static final Logger logger = LoggerFactory.getLogger(OrderFormNsyn.class);

	@Autowired
	ROAOrderFormService rOAOrderFormService;

	@Autowired
	PaymentService paymentService;

	@Autowired
	ROAUserAccountService rOAUserAccountService;

	/**
	 * 异步更新订单价格
	 * 
	 * @param orderNumArray
	 */
	public void updateOrderPrice(final String orderNumArray) {
		final Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					logger.info("异步插入单个订单价格，订单号：" + orderNumArray);
					final String[] tempOrderNumArray = orderNumArray.split("\\,");
					if (tempOrderNumArray != null && tempOrderNumArray.length > 0) {
						for (int i = 0; i < tempOrderNumArray.length; i++) {
							final OrderFormEntity orderFormEntity = rOAOrderFormService.getOrderFormByOrderNum(tempOrderNumArray[i]);
							if (orderFormEntity != null) {
								final PaymentEntity paymentEntity = paymentService.selectByOrderNumAndTradeType(tempOrderNumArray[i], Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0,
										Constants.PAYMENT_STATUS.STAUS2,null);
								if (paymentEntity != null) {
									paymentEntity.setOrderPrice(orderFormEntity.getTotalAmount());
									paymentService.updateByPrimaryKeySelective(paymentEntity);
								}
							}
						}
					}
				} catch (Exception e) {
					logger.error("异步更新订单价格出错  OrderFormNsyn.updateOrderPrice " + e.getMessage());
				}

			}
		};
		thread.start();
	}

	/**
	 * @Description: 
	 *               此方法功能同getAccountInfoByOrderNo，但本地定义的payChannel与userAccount类型不对应
	 *               ，故转换
	 * @param orderNo
	 * @param paychannel
	 *            支付方式 0：支付宝，1:微信，2：银行卡
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年6月4日下午1:51:05
	 **/
	public UserAccountDto getAccountInfoByOrderNoLocal(String orderNo, Integer paychannel) {
		Integer accountType = paychannel;
		if (paychannel == 0)
			accountType += 1;
		return getAccountInfoByOrderNo(orderNo, accountType);
	}

	/**
	 * @Description: 查询对应订单号的导购个人可使用支付账号信息
	 * @param orderNo
	 * @param accountType
	 *            账号类型 1：支付宝，2：银行卡
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年6月4日下午1:49:08
	 **/
	public UserAccountDto getAccountInfoByOrderNo(String orderNo, Integer accountType) {
		try {
			List<UserAccountDto> list = getAccountInfo(orderNo);
			for (UserAccountDto userAccountDto : list) {
				if ("0".equals(userAccountDto.getIsDisabled()) && "0".equals(userAccountDto.getUserIsDisabled()) && accountType.equals(userAccountDto.getType()))
					return userAccountDto;
			}
		} catch (Exception e) {
			logger.error("查询导购支付账号失败");
			e.printStackTrace();
		}
		throw new RuntimeException("订单号-->" + orderNo + "  的订单未查询到导购的" + (accountType == 1 ? "支付宝" : "银行卡") + "账号信息");
	}

	/**
	 * @Description: 验证账号是否正常
	 * @param orderNo
	 * @param payChannel支付方式
	 *            0：支付宝，1：银行卡
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年6月4日下午2:46:02
	 **/
	public int validateAccount(String orderNo, Integer payChannel) {
		Integer accountType = payChannel + 1;
		List<UserAccountDto> list = getAccountInfo(orderNo);
		for (UserAccountDto userAccountDto : list) {
			if ("0".equals(userAccountDto.getIsDisabled()) && "0".equals(userAccountDto.getUserIsDisabled()) && accountType.toString().equals(userAccountDto.getType())) {
				return Constants.ACCOUNT_STATUS.ACCOUNT_STATUS0;// 账户正常
			}
		}
		if (list != null && !list.isEmpty()) {
			UserAccountDto userAccountDto = list.get(0);
			if ("1".equals(userAccountDto.getUserIsDisabled()) && accountType.toString().equals(userAccountDto.getType()))
				return Constants.ACCOUNT_STATUS.ACCOUNT_STATUS1;// 登陆账号禁用
			else if ("1".equals(userAccountDto.getIsDisabled()) && accountType.toString().equals(userAccountDto.getType()))
				return Constants.ACCOUNT_STATUS.ACCOUNT_STATUS2;// 支付账户禁用
		}
		return Constants.ACCOUNT_STATUS.ACCOUNT_STATUS3;// 账号不存在

	}

	private List<UserAccountDto> getAccountInfo(String orderNo) {
		List<UserAccountDto> newList = new ArrayList<UserAccountDto>();
		try {
			OrderFormEntity orderFormEntity = rOAOrderFormService.getOrderFormByOrderNum(orderNo);
			if (orderFormEntity != null && StringUtils.isNotEmpty(orderFormEntity.getGuideId())) {
				return rOAUserAccountService.getAccountListByGuideId(Integer.valueOf(orderFormEntity.getGuideId()));

			}
		} catch (Exception e) {
			logger.error("查询导购支付账号失败");
			e.printStackTrace();
		}
		return newList;
	}
}
