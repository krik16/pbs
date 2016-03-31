$.ajaxSetup({
    cache: false
});
var url_ = "../mchShop/list";
$(document).ready(function() {
	ajaxCommonSearch(url_,getSearchEntity());

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
	 return searchEntity;
}

function save(){
	var id = $("#id").val();
	var name = $("#name").val();
	var address = $("#address").val();
	var companyId = $("#companyId").val();
	var subCompanyId = $("#subCompanyId").val();
	var agentId = $("#agentId").val();
	var desc = $("#desc").val();
	var weixinMchId = $("#weixinMchId").val();
	var aliPid = $("#aliPid").val();
	var aliKey = $("#aliKey").val();

	if(!name){
		Modal.alert({
			msg: "名称不能为空!"
		});
		return;
	}

	$.post("../mchShop/save", {
			id : id,
			name : name,
			address : address,
			companyId : companyId,
			subCompanyId : subCompanyId,
			agentId : agentId,
			desc : desc,
			weixinMchId : weixinMchId,
			aliPid : aliPid,
			aliKey : aliKey
	}, function(data) {
		if(data.meta.errno != 0){
			Modal.alert({
				msg: "操作失败,"+data.meta.msg
			});
			return;
		}
		window.history.go(-1);
		ajaxCommonSearch(url_,getSearchEntity());
	}, "json");
}

function cance(id){
	Modal.confirm(
		{
			msg: "是否确认删除？"
		})
		.on( function (e) {
			if(e) {
				$.post("../mchShop/cance?id=" + id, function (data) {
					if (data.meta.errno != 0) {
						Modal.alert({
							msg: "操作失败,"+data.meta.msg
						});
						return;
					}
					ajaxCommonSearch(url_, getSearchEntity());
				}, "json");
			}
		});
}

function companySelect(url,parentId,eId){
	var pid = document.getElementById(parentId);
	if(pid.value > 0){
	$("#subCompany-select").css("display","inline-block");
    $("#agent-select").css("display","none");
	$("#agentId").val(0);
	}else{
		$("#subCompany-select").css("display","none");
		var authority = $('#authority').val();
		if(authority != 'MCH_COMPANY' && authority != 'MCH_SUB_COMPANY'){
			$("#agent-select").css("display","inline-block");
			selectChange("../agent/getAll",parentId,"agentId");
		}
	}
	selectChange(url,parentId,eId);
}

function agentSelect(url,parentId,eId){
	var pid = document.getElementById(parentId);
	if(pid.value > 0){
		$("#company-select").css("display","none");
		$("#companyId").val(0);
		$("#subCompany-select").css("display","none");
		$("#subCompanyId").val(0);
	}else{
		$("#company-select").css("display","inline-block");
		selectChange("../mchCompany/getAll",parentId,"companyId");
	}
	//selectChange(url,parentId,eId);
}
