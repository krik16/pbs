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
	var stockholderId = $("#stockholderId").val();
	var areaId = $("#areaId").val();
	var agentId = $("#agentId").val();

	if(!userAccount){
		Modal.alert({
			msg: "登录账号不能为空!"
		});
		return;
	}

	var re = /^1\d{10}$/
	if (!re.test(userAccount)) {
		Modal.alert({
			msg: "账号必须为正确的手机号"
		});
		return;
	}

	if(!userName){
		Modal.alert({
			msg: "用户姓名不能为空!"
		});
		return;
	}
	/*if(!userPhone){
		Modal.alert({
			msg: "电话号码不能为空!"
		});
		return;
	}*/
	if(roleId<=0){
		Modal.alert({
			msg: "用户角色不能为空!"
		});
		return;
	}

	if(roleId == 1 && stockholderId <= 0){
		Modal.alert({
			msg: "股东不能为空!"
		});
		return;
	}
	if(roleId == 2 && areaId <= 0){
		Modal.alert({
			msg: "区域不能为空!"
		});
		return;
	}
	if(roleId == 3 && agentId <= 0){
		Modal.alert({
			msg: "代理不能为空!"
		});
		return;
	}


	$.post("../user/save", {
			id : id,
			userAccount : userAccount,
			userName : userName,
			//userPhone : userPhone,
			isEmployee : isEmployee,
			roleId : roleId,
			companyId : companyId,
			subCompanyId : subCompanyId,
			shopId : shopId,
			stockholderId : stockholderId,
			areaId : areaId,
			agentId : agentId
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
					Modal.alert({
						msg: "重置密码成功"
					});
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
		//$("#company-select").css("display","inline-block");
		//$("#shop-select").css("display","inline-block");
	}else{
		$("#area-select").css("display","none");
		$("#areaId").val(0);
		$("#agent-select").css("display","none");
		$("#agentId").val(0);

		$("#company-select").css("display","none");
		$("#companyId").val(0);
		$("#subCompany-select").css("display","none");
		$("#subCompanyId").val(0);
		$("#shop-select").css("display","none");
		$("#shopId").val(0);

	}
	selectChange("../user/getRoleByType",parentId,'roleId');
}

function roleSelect(parentId){
	var pid = document.getElementById(parentId);
	$("#area-select").css("display","none");
	$("#areaId").val(0);
	$("#agent-select").css("display","none");
	$("#agentId").val(0);
	$("#stockholder-select").css("display","none");
	$("#stockholderId").val(0);
	if(pid.value >= 1 && pid.value < 4){
		if(pid.value == 1){//股东
			$("#stockholder-select").css("display","inline-block");
			selectChange("../stockholder/getAll",parentId,"stockholderId");
		}
		if(pid.value >= 2){//区域
			$("#area-select").css("display","inline-block");
			selectChange("../area/getAll",parentId,"areaId");
		}
		if(pid.value >= 3){//代理
			$("#agent-select").css("display","inline-block");
			selectChange("../agent/getByAreaId","areaId","agentId");
		}
	}
}


function areaSelect(url,parentId,eId){
	selectChange(url,parentId,eId);
}
