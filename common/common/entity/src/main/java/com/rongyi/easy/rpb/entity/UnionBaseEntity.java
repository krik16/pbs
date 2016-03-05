/**
 * @Copyright (C),上海容易网电子商务有限公司	
 * @Author: 柯军 
 * @datetime:2015年7月3日下午2:15:11
 * @Description: TODO
 *
 **/

package com.rongyi.easy.rpb.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.rongyi.core.common.util.DateUtil;

/**
 * @Author: 柯军
 * @Description: 银联接口数据常规项
 * @datetime:2015年7月3日下午2:15:11
 * 
 **/

public class UnionBaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4335010361815223836L;

	private Integer id;

	/**
	 * 报文类型
	 */
	private String msgType;

	/**
	 * 交易代码 002002:一次查询，002003二次查询
	 */
	private String msgTxnCode;

	/**
	 * 报文流水号
	 */
	private String msgCrrltnId;

	/**
	 * 报文发送方
	 */
	private String msgSender;

	/**
	 * 报文日期
	 */
	private Date msgTime;

	/**
	 * 平台流水号
	 */
	private String msgSysSn;

	/**
	 * 报文版本号
	 */
	private String msgVer;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsgTxnCode() {
		return msgTxnCode;
	}

	public void setMsgTxnCode(String msgTxnCode) {
		this.msgTxnCode = msgTxnCode;
	}

	public String getMsgCrrltnId() {
		return msgCrrltnId;
	}

	public void setMsgCrrltnId(String msgCrrltnId) {
		this.msgCrrltnId = msgCrrltnId;
	}

	public String getMsgSender() {
		return msgSender;
	}

	public void setMsgSender(String msgSender) {
		this.msgSender = msgSender;
	}

	public Date getMsgTime() {
		return msgTime;
	}

	public void setMsgTime(Date msgTime) {
		this.msgTime = msgTime;
	}

	public String getMsgSysSn() {
		return msgSysSn;
	}

	public void setMsgSysSn(String msgSysSn) {
		this.msgSysSn = msgSysSn;
	}

	public String getMsgVer() {
		return msgVer;
	}

	public void setMsgVer(String msgVer) {
		this.msgVer = msgVer;
	}

	public static UnionBaseEntity mapToBaseEntity(Map<String, String[]> paramMap) {
		UnionBaseEntity unionBaseEntity = new UnionBaseEntity();
		if (paramMap.get("msg_type") != null && paramMap.get("msg_type").length > 0)
			unionBaseEntity.setMsgType(paramMap.get("msg_type")[0].toString());
		if (paramMap.get("msg_txn_code") != null && paramMap.get("msg_txn_code").length > 0)
			unionBaseEntity.setMsgTxnCode(paramMap.get("msg_txn_code")[0].toString());
		if (paramMap.get("msg_crrltn_id") != null && paramMap.get("msg_crrltn_id").length > 0)
			unionBaseEntity.setMsgCrrltnId(paramMap.get("msg_crrltn_id")[0].toString());
		if (paramMap.get("msg_sender") != null && paramMap.get("msg_sender").length > 0)
			unionBaseEntity.setMsgSender(paramMap.get("msg_sender")[0].toString());
		if (paramMap.get("msg_time") != null && paramMap.get("msg_time").length > 0)
			unionBaseEntity.setMsgTime(DateUtil.getCurrDateTime());
		if (paramMap.get("msg_sys_sn") != null && paramMap.get("msg_sys_sn").length > 0)
			unionBaseEntity.setMsgSysSn(paramMap.get("msg_sys_sn")[0].toString());
		if (paramMap.get("msg_ver") != null && paramMap.get("msg_ver").length > 0)
			unionBaseEntity.setMsgVer(paramMap.get("msg_ver")[0].toString());
		return unionBaseEntity;
	}
}
