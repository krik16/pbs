<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>查询商品</title>
<link href="${ctx}/css/index.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/css/poshytip/tip-yellowsimple/tip-yellowsimple.css"
	type="text/css" rel="stylesheet" />
<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
<script src="${ctx}/js/util.js" type="text/javascript"></script>
<script src="${ctx}/js/msgBoxBg.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery.poshytip.min.js" type="text/javascript"></script>
<script src="${ctx}/js/payManager/payManager-info.js" type="text/javascript"></script>

<style type="text/css">
body {
	background: #fff;
}
</style>
</head>
<body>
        <div class="memSuper">
            <div class="memSuper-title">支付</div>
            <div class="memSuper-main">
                <div class="search" style="height: 60px;">
                    <input id="currpage" type="hidden" name="currpage" value="${currpage}" />
                    <div class="memSuper-search" style="height: 130px; width: 100%">
					<input type="hidden" value="1" name="pageNo" id="currpage">
					<ur>
							
						<li>付款单号:
							<input id="payNo" class="detail" type="text" maxlength="50"/></input>
						</li>
						<li>
							订单号:
							<input id="orderNum"  class="detail" type="text" maxlength="50" /></input>
						</li>
						<li>
							打款方式：
							<select id="tradeType" style="width: 100px">
								<option value="">--全部--</option>
								<option value="1">退款</option>
								<option value="2">付款</option>
								<option value="0">购买</option>
							</select>
						</li>
						<li>
							状态：
							<select id="status" style="width: 100px">
								<option value="">--全部--</option>
								<option value ="0">待打款</option>
								<!-- <option value="1">款已打</option> -->
								<option value="2">款已到</option>
							</select>
						</li>
						<li>
						<a href="javascript:void(0);" class="btnsearch checked" id="search-button">查询</a>
						</li>
						<li>
						<a href="javascript:void(0);"class="btnsearch checked" id="batch-refund" style="margin-right: 20px" onclick="batchPay(1)">批量退款</a>
						</li>
						<li>
						<a href="javascript:void(0);"class="btnsearch checked" id="batch-pay" onclick="batchPay(2)">批量付款</a>
						</li>
					</ur>
				</div>
                </div>
                <div id="result" style="margin: 0 30px;"></div>
            </div>
        </div>
    </body>

<body>
	<div class="shops" style="height: auto;">
	<input type="hidden" value="${currpage}" name="currpage" id="currpage">
	<div class="shopSelect" style="padding: 20px 0 0 0;">
		<span style="display: inline-block; float: left; line-height: 40px; padding-right: 10px;">付款单号：</span>
		<input id="payNo" type="text" class="search-input" value="${payNo}"maxlength="24" onkeypress="keypressEvent(event)"/>
		
		<span style="display: inline-block; float: left; line-height: 40px; padding-right: 10px;padding-left: 10px"">订单号：</span>
		<input id="orderNum" type="text" class="search-input" value="${orderNum}"maxlength="24" onkeypress="keypressEvent(event)" />
		
		<span style="display: inline-block; float: left; line-height: 40px; padding-right: 10px; padding-left: 10px">交易类型：</span>
		<select id="tradeType" class="search-input">
			<option value =""></option>
			<option value ="1">退款</option>
			<option value="2">付款</option>
			<option value ="0">购买</option>
		</select>
		<span style="display: inline-block; float: left; line-height: 40px; padding-right: 10px; padding-left: 10px">状态：</span>
		<select id="status" class="search-input">
			<option value =""></option>
			<option value ="0">待打款</option>
			<!-- <option value="1">款已打</option> -->
			<option value="2">款已到</option>
		</select> 
		 
		<a href="javascript:void(0);"class="search-button" id="search-button">搜索</a>
	</div>
	<div class="shopSelect" style="padding: 20px 0 0 0;">
			<a href="javascript:void(0);"class="search-button" id="batch-refund" style="margin-right: 20px" onclick="batchPay(1)">批量退款</a>
			<a href="javascript:void(0);"class="search-button" id="batch-pay" onclick="batchPay(2)">批量付款</a>
	</div>
	<div id="result" style="margin: 0 30px;"></div>
	</div>

<!-- 	<div class="MsgBoxBg"></div>
	<div class="MsgBoxBgTip"></div>
	<div class="MsgBoxConfirm">
		<div class="MsgBoxTitle">提示</div>
		<div class="MsgBoxMain">
			<p></p>
			<a href="javascript:void(0);" class="Button cancel">取消</a> <a
				href="javascript:void(0);" class="Button checked" id="MsgBoxBtn">确定</a>
		</div>
	</div>

	<div class="MsgBoxTip">
		<div class="MsgBoxTitle">提示</div>
		<div class="MsgBoxMain">
			<p></p>
			<a style="margin: 0 auto; display: block; float: none;"
				href="javascript:closeBg();" class="Button checked"
				id="MsgBoxTipBtn">确定</a>
		</div>
	</div> -->

</body>
</html>
