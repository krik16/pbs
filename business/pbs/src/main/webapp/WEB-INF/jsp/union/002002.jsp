<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<html>
	<body>
		<form action="../union/validateCoupon" method="post">
			<input type="hidden" name="msg_type" value="00" />
			<input type="hidden" name="msg_txn_code" value="002002" />
			<input type="hidden" name="msg_crrltn_id" value="20130921155443030785" />
			<input type="hidden" name="msg_flg" value="0" />
			<input type="hidden" name="msg_sender" value="1" />
			<input type="hidden" name="msg_time" value="20130921155423" />
			<input type="hidden" name="msg_sys_sn" value="20130921155443030785" />
			<input type="hidden" name="msg_ver" value="0.1" />
			<input type="hidden" name="req_serial_no" value="20130921155443030785" />
			<input type="hidden" name="shop_no" value="086310100000000099" />
			<input type="hidden" name="term_no" value="12340004" />
			<input type="hidden" name="coupon_no" value="3173123400041" />
			<input type="hidden" name="enc_card_no" value="9d224e72968b8a495606eff46fec2016" />
			<input type="hidden" name="part_card_no" value="620048******2291" />
			<input type="hidden" name="use_shares" value="1" />
			<input type="hidden" name="term_sp_chnl_no" value="001" />
			<input type="hidden" name="trans_crrltn_no" value="1234567890" />
			<input type="hidden" name="sign" value ="" />
			<input type="submit" value="002002" />
		</form>
	</body>
</html>