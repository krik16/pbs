<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML>
<head>
<title>活动注册</title>
</head>

<body>
		<!--action请自行添加本地接口地址-->
		<form action="../unionMarketing/union/unionpayMarketing" method="post">
			<input type="hidden" name="msg_type" value="10" />
			<input type="hidden" name="msg_txn_code" value="101001" />
			<input type="hidden" name="msg_crrltn_id" value="20130921155443030785" />
			<input type="hidden" name="msg_flg" value="0" />
			<input type="hidden" name="msg_sender" value="173" />
			<input type="hidden" name="msg_time" value="20150703175510" />
			<input type="hidden" name="msg_sys_sn" value="20130921155443030785" />
			<input type="hidden" name="msg_ver" value="0.1" />
			<input type="hidden" name="sp_chnl_no" value="173" />
			<input type="hidden" name="event_no" value="1111111110" />
			<input type="hidden" name="shop_no" value="111" />
			<input type="hidden" name="event_title" value="活动主题" />
			<input type="hidden" name="event_desc" value="活动介绍" />
			<input type="hidden" name="begin_date" value="20150703175510" />
			<input type="hidden" name=end_date value="20150703175510" />
			<input type="hidden" name="event_link" value="https://rongyi.com" />
			<input type="hidden" name="event_rule" value="活动规则" />
			<input type="hidden" name="rule_desc" value="规则描述" />
			<input type="hidden" name="spec_bank_flag" value="0" />
			<input type="hidden" name="event_status" value="1" />
			<!--在本地测试时建议先不做验签，签名可保持为空-->
			<input type="hidden" name="sign" value ="" />
			<input type="submit" value="提交测试" />
		</form>
	</body>
</html>
