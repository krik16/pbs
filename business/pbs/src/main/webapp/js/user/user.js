$.ajaxSetup({
    cache: false
});
var url_ = "../user/list";
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

	$.post("../user/save", {
			id : id,
			userAccount : userAccount,
			userName : userName,
			userPhone : userPhone,
			isEmployee : isEmployee,
			roleId : roleId,
			companyId : companyId,
			subCompanyId : subCompanyId,
			shopId : shopId,
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
				$.post("../user/cance?id=" + id, function (data) {
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
				$.post("../user/resetPwd?id=" + id, function (data) {
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


function isEmployeeSelect(parentId){
	var pid = document.getElementById(parentId);
	if(pid.value >=0){
		$("#role-select").css("display","inline-block");
	}else{
		$("#role-select").css("display","none");
	}

	if(pid.value > 0){
		$("#company-select").css("display","inline-block");
		$("#shop-select").css("display","inline-block");
	}else{
		$("#company-select").css("display","none");
		$("#subCompany-select").css("display","none");

	}
	selectChange("../user/getRoleByType",parentId,'roleId');
}


function companySelect(url,parentId,eId){
	var pid = document.getElementById(parentId);
	if(pid.value > 0){
		$("#subCompany-select").css("display","inline-block");
	}else{
		$("#subCompany-select").css("display","none");
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