package com.rongyi.rpb.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.rongyi.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.rongyi.easy.rpb.entity.UnionActivityRegister;
import com.rongyi.rpb.common.pay.union.UnionUtil;
import com.rongyi.rpb.constants.ConstantEnum;
import com.rongyi.rpb.service.UnionpayMarketingService;
import com.unionpay.acp.sdk.HttpClient;
import com.unionpay.acp.sdk.SDKUtil;

/**
 * 
 * @author wangzhiwen
 * 
 */
@Service
public class UnionpayMarketingServiceImpl extends BaseServiceImpl implements UnionpayMarketingService {
	private static final String NAMESPACE = "com.rongyi.rpb.mapper.UnionActivityRegisterMapper";

	@Override
	public Map<String, Object> validateXML(Map<String, String[]> paramMap) throws ParseException {
		UnionActivityRegister unionActivityRegister = UnionActivityRegister.mapToEntity(paramMap);
		Map<String, String> respMap = responseUnionMessage(unionActivityRegister);
		System.err.println("返回报文：" + JSONObject.fromObject(respMap).toString());
		insert(unionActivityRegister);
		return null;
	}

	/**
	 * 活动注册
	 */
	@Override
	public void insert(UnionActivityRegister unionActivityRegister) {
		this.getBaseDao().insertBySql(NAMESPACE + ".insert", unionActivityRegister);
	}

	// 组织报文发送银联商务
	public Map<String, String> responseUnionMessage(UnionActivityRegister unionActivityRegister) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("msg_type", unionActivityRegister.getMsgType());// 报文类型
		data.put("msg_txn_code", unionActivityRegister.getMsgTxnCode());// 交易代码
		data.put("msg_crrltn_id", unionActivityRegister.getMsgCrrltnId());// 消息关联号
		data.put("msg_flg", unionActivityRegister.getMsgFlg());// 报文请求应答标志
		data.put("msg_sender", unionActivityRegister.getMsgSender());// 报文发送方
		data.put("msg_time", new SimpleDateFormat("yyyyMMddHHmmss").format(unionActivityRegister.getMsgTime()));// 报文日期
		data.put("msg_sys_sn", unionActivityRegister.getMsgSysSn());// 平台流水号
		data.put("msg_ver", unionActivityRegister.getMsgVer());// 报文版本号
		data.put("sp_chnl_no", unionActivityRegister.getSpChnlNo());// 渠道码
		data.put("event_no", unionActivityRegister.getEventNo());// 活动号
		data.put("event_title", unionActivityRegister.getEventTitle());// 活动主题
		data.put("begin_date", new SimpleDateFormat("yyyyMMddHHmmss").format(unionActivityRegister.getCreateAt()));// 开始时间
		data.put("end_date", new SimpleDateFormat("yyyyMMddHHmmss").format(unionActivityRegister.getEndAt()));// 结束时间
		data.put("event_rule", unionActivityRegister.getEventRule());// 活动规则
		data.put("rule_desc", unionActivityRegister.getRuleDesc());// 规则描述
		data.put("event_desc", unionActivityRegister.getEventDesc());// 活动介绍 非必传
		data.put("event_link", unionActivityRegister.getEventLink());// 活动链接 非必传
		data.put("spec_bank_flag", unionActivityRegister.getSpecBankFlag());// 限制银行
																			// 非必传
		data.put("event_status", unionActivityRegister.getEventStatus());// 活动状态
		String parmaPlain = UnionUtil.getParamPlain3(data);
		String sign = com.chinaums.ysmktaln.mktaln4sp.Service.sign(parmaPlain, ConstantEnum.UNION_PRIVATE_KEY.getValueStr());
		data.put("shop_no", unionActivityRegister.getShopNo());// 门店号
		data.put("sign", sign);// 签名数据
		System.err.println("requestXML-submitDataInfo：" + JSONObject.fromObject(data).toString());
		Map<String, String> resMap = submitDataInfo(data, "http://dev.spserv.yxlm.chinaums.com:17201/spservice/spevent/process");
		return resMap;
	}

	/**
	 * @param submitFromData
	 * @return 返回报文 map
	 */
	public static Map<String, String> submitDataInfo(Map<String, String> submitFromData, String requestUrl) {
		String resultString = "";
		// 发送
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
		try {
			int status = hc.send(submitFromData, "UTF-8");
			if (200 == status) {
				resultString = hc.getResult();
				System.err.println("responseXML-submitDataInfo：" + JSONObject.fromObject(resultString).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> resData = new HashMap<String, String>();
		// 返回数据是否合法
		if (null != resultString && !"".equals(resultString)) {
			// 将返回结果转换为map
			resData = SDKUtil.convertResultStringToMap(resultString);
			// if (SDKUtil.validate(resData, "utf-8")) {
			// // if (UnionUtil.verify(resData.toString(),
			// // resData.get("sign"))) {
			// System.out.println("返回数据合法性");
			// } else {
			// System.out.println("验证签名失败");
			// }
			// 打印返回报文
			System.out.println("打印返回报文：" + resultString);
		}
		return resData;
	}

	// 根据门店号查询活动号
	@Override
	public UnionActivityRegister selectByShopNo(String shop_no) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shopNo", shop_no);
		return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectByShopNo", map);
	}

	// 注销活动号
	@Override
	public void deleteByEventStatus(Map<String, String[]> paramMap) throws ParseException {
		UnionActivityRegister unionActivityRegister = UnionActivityRegister.deleteActivity(paramMap);

		Map<String, String> data = new HashMap<String, String>();
		data.put("msg_type", unionActivityRegister.getMsgType());// 报文类型
		data.put("msg_txn_code", unionActivityRegister.getMsgTxnCode());// 交易代码
		data.put("msg_crrltn_id", unionActivityRegister.getMsgCrrltnId());// 消息关联号
		data.put("msg_flg", unionActivityRegister.getMsgFlg());// 报文请求应答标志
		data.put("msg_sender", unionActivityRegister.getMsgSender());// 报文发送方
		data.put("msg_time", new SimpleDateFormat("yyyyMMddHHmmss").format(unionActivityRegister.getMsgTime()));// 报文日期
		data.put("msg_sys_sn", unionActivityRegister.getMsgSysSn());// 平台流水号
		data.put("event_title", unionActivityRegister.getEventTitle());// 活动主题
		data.put("msg_ver", unionActivityRegister.getMsgVer());// 报文版本号
		data.put("sp_chnl_no", unionActivityRegister.getSpChnlNo());// 渠道码
		data.put("event_no", unionActivityRegister.getEventNo());// 活动号
		data.put("event_rule", unionActivityRegister.getEventRule());// 活动规则
		data.put("begin_date", new SimpleDateFormat("yyyyMMddHHmmss").format(unionActivityRegister.getCreateAt()));// 开始时间
		data.put("end_date", new SimpleDateFormat("yyyyMMddHHmmss").format(unionActivityRegister.getEndAt()));// 结束时间
		data.put("event_status", unionActivityRegister.getEventStatus());// 活动状态
		String parmaPlain = UnionUtil.getParamPlain3(data);
		String sign = com.chinaums.ysmktaln.mktaln4sp.Service.sign(parmaPlain, ConstantEnum.UNION_PRIVATE_KEY.getValueStr());
		data.put("shop_no", unionActivityRegister.getShopNo());// 门店号
		data.put("sign", sign);// 签名数据
		Map<String, String> resMap = submitDataInfo(data, "http://dev.spserv.yxlm.chinaums.com:17201/spservice/spevent/process");
		System.err.println("deleteByEventStatus-responseXML：" + JSONObject.fromObject(resMap).toString());
		// this.getBaseDao().updateBySql(NAMESPACE +
		// ".updateByPrimaryKeySelective", unionActivityRegister);
	}

}
