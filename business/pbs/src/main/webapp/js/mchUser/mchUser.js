$.ajaxSetup({
    cache: false
});
var url_ = "../mchUser/list";
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
	var userAccount = $("#userAccount").val();
	var userName = $("#userName").val();
	var userPhone = $("#userPhone").val();
	var isEmployee = $("#isEmployee").val();
	var roleId = $("#roleId").val();
	var companyId = $("#companyId").val();
	var subCompanyId = $("#subCompanyId").val();
	var shopId = $("#shopId").val();
	var areaId = $("#areaId").val();
	var agentId = $("#agentId").val();

	if(!userAccount){
		Modal.alert({
			msg: "登录账号不能为空!"
		});
		return;
	}
	if(!userName){
		Modal.alert({
			msg: "用户姓名不能为空!"
		});
		return;
	}
	if(!userPhone){
		Modal.alert({
			msg: "电话号码不能为空!"
		});
		return;
	}
	if(isEmployee<0){
		Modal.alert({
			msg: "用户类型不能为空!"
		});
		return;
	}
	if(roleId<=0){
		Modal.alert({
			msg: "用户角色不能为空!"
		});
		return;
	}


	$.post("../mchUser/save", {
			id : id,
			userAccount : userAccount,
			userName : userName,
			userPhone : userPhone,
			isEmployee : isEmployee,
			roleId : roleId,
			companyId : companyId,
			subCompanyId : subCompanyId,
			shopId : shopId,
			areaId : areaId,
			agentId : agentId,
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
				$.post("../mchUser/cance?id=" + id, function (data) {
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
function reset(id){
	Modal.confirm(
		{
			msg: "是否确认重置密码？"
		})
		.on( function (e) {
			if(e) {
				$.post("../mchUser/resetPwd?id=" + id, function (data) {
					if (data.meta.errno != 0) {
						Modal.alert({
							msg: "操作失败,"+data.meta.msg
						});
						return;
					}
					Modal.alert({
						msg: "重置密码成功"
					});
					ajaxCommonSearch(url_, getSearchEntity());
				}, "json");
			}
		});

}


//function isEmployeeSelect(parentId){
//	var pid = document.getElementById(parentId);
//	if(pid.value >=0){
//		$("#role-select").css("display","inline-block");
//	}else{
//		$("#role-select").css("display","none");
//	}
//
//	if(pid.value > 0){
//		//$("#company-select").css("display","inline-block");
//		//$("#shop-select").css("display","inline-block");
//	}else{
//		$("#company-select").css("display","none");
//		$("#companyId").val(0);
//		$("#subCompany-select").css("display","none");
//		$("#subCompanyId").val(0);
//		$("#shop-select").css("display","none");
//		$("#shopId").val(0);
//
//	}
//	selectChange("../mchUser/getRoleByType",parentId,'roleId');
//}

function roleSelect(parentId){
	var pid = document.getElementById(parentId);

	$("#company-select").css("display","none");
	$("#companyId").val(0);
	$("#subCompany-select").css("display","none");
	$("#subCompanyId").val(0);
	$("#shop-select").css("display","none");
	$("#shopId").val(0);

	if(pid.value >= 4){
		if(pid.value >= 4 || pid.value == 8) {//商户总公司
			$("#company-select").css("display","inline-block");
			//selectChange("../mchCompany/getAll","areaId","agentId");
		}
		if(pid.value >= 5 && pid.value < 8) {//分分公司
			$("#subCompany-select").css("display","inline-block");
			selectChange('../mchSubCompany/getByCompanyId','companyId','subCompanyId');
		}
		if(pid.value == 6 || pid.value == 7){//门店
			$("#shop-select").css("display","inline-block");
			selectChange('../mchShop/getBySubCompanyId','subCompanyId','shopId');
		}
	}
}


function areaSelect(url,parentId,eId){
	//var pid = document.getElementById(parentId);
	//if(pid.value > 0){
	//	$("#agent-select").css("display","inline-block");
	//}else{
	//	$("#agent-select").css("display","none");
	//	$("#agent-select").value=0;
	//}
	selectChange(url,parentId,eId);
}

function companySelect(url,parentId,eId){
	var pid = document.getElementById(parentId);
	var roleId = $("#roleId").val();
	if(pid.value > 0 && roleId > 4 && roleId < 8){
		$("#subCompany-select").css("display","inline-block");
	}else{
		$("#subCompany-select").css("display","none");
		$("#subCompany-select").value=0;
	}
	//改变分公司选项
	selectChange(url,parentId,eId);
	//改变店铺选项
	selectChange('../mchShop/getByCompanyId',parentId,'shopId');
}


function subCompanySelect(url,parentId,eId){
	//var pid = document.getElementById(parentId);
	selectChange(url,parentId,eId);
}