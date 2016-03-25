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
	var mchCompanyId = $("#mchCompanyId").val();
	var mchSubCompanyId = $("#mchSubCompanyId").val();
	var mchShopId = $("#mchShopId").val();

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
			mchCompanyId : mchCompanyId,
			mchSubCompanyId : mchSubCompanyId,
			mchShopId : mchShopId,
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
				$.post("../user/cance?id=" + id, {}, function (data) {
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