$.ajaxSetup({
	cache: false
});
var url_ = "../bill/list";
$(document).ready(function() {
	ajaxCommonSearch(url_,getSearchEntity());
	$("#outTotalCount").val()
	$('.startTime').datetimepicker({
		timeFormat: "HH:mm:ss",
		dateFormat: "yy-mm-dd",
		onSelect: function (dateText, inst) {
			var dt = new Date(Date.parse(dateText.replace(/-/g, "/")));
			$('#tradeStartTime').val(dt);
		}
	});
	$('.endTime').datetimepicker({
		timeFormat: "HH:mm:ss",
		dateFormat: "yy-mm-dd",
		onSelect: function (dateText, inst) {
			var dt = new Date(Date.parse(dateText.replace(/-/g, "/")));
			$('#tradeEndTime').val(dt);

		}
	});
});

function search(){
	$("#currpage").val("1");
	ajaxCommonSearch(url_,getSearchEntity());
}
/**
 * 查询参数
 */
function getSearchEntity(){
	var currentPage = $('#currpage').val();
	if(currentPage == null || currentPage == ''){
		currentPage = 1;
	}
	var searchEntity = new Object();
	searchEntity.currpage = currentPage;
	searchEntity.name = $("#name").val();
	searchEntity.orderNo = $("#orderNo").val();
	searchEntity.tradeNo = $("#tradeNo").val();
	searchEntity.payChannel = $("#payChannel").val();
	searchEntity.tradeType = $("#tradeType").val();
	searchEntity.tradeStartTime = $("#tradeStartTime").val();
	searchEntity.tradeEndTime = $("#tradeEndTime").val();
	searchEntity.shopName = $("#shopName").val();
	searchEntity.shopId = $("#shopId").val();
	searchEntity.companyId = $("#companyId").val();
	searchEntity.agentId = $("#agentId").val();
	searchEntity.areaId = $("#areaId").val();
	return searchEntity;
}

function areaSelect(url,parentId,eId){
	var pid = document.getElementById(parentId);
	if(pid.value > 0) {
		selectChange(url, parentId, eId);
	}
}
function agentSelect(url,parentId,eId){
	var pid = document.getElementById(parentId);
	if(pid.value > 0) {
		selectChange(url, parentId, eId);
	}
}

function companySelect(url,parentId,eId){
	var pid = document.getElementById(parentId);
	if(pid.value > 0) {
		selectChange(url, parentId, eId);
	}
}

