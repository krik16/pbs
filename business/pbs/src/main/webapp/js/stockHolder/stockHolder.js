$.ajaxSetup({
    cache: false
});
var url_ = "../stockholder/list";
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
	var desc = $("#desc").val();

	if(!name){
		Modal.alert({
			msg: "名称不能为空!"
		});
		return;
	}

	$.post("../stockholder/save", {
			id : id,
			name : name,
			desc : desc
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
				$.post("../stockholder/cance?id=" + id, function (data) {
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