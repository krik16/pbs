$.ajaxSetup({
    cache: false
});
function ajaxCommonSearch(url_,searchEntity) {
    var jsonData = JSON.stringify(searchEntity);
    $.ajax({
        url: url_,
        type: 'post',
        contentType: "application/json; charset=UTF-8",
        data: jsonData,
        success: function(data) {
            $("#result").html(data);
            $("#search-button").click(function() {
                searchEntity = getSearchEntity();
                searchEntity.currpage = 1;
                ajaxCommonSearch(url_,searchEntity);
            });

            $("#downPage").click(function() {
                searchEntity.currpage  = Number($("#currpage").val())-1;
                if (searchEntity.currpage <= 0) {
                    return false;
                }else {
                    $("#currpage").val(searchEntity.currpage);
                    ajaxCommonSearch(url_, searchEntity);
                }
            });

            $("#fastPage").click(function() {
                if ( Number($("#currpage").val()) <= 1) {
                    return false;
                }else{
                searchEntity.currpage = 1;
                $("#currpage").val(1);
                ajaxCommonSearch(url_,searchEntity);
                }
            });

            $("#upPage").click(function() {
                var currpageVal = parseInt($("#currpage").val());
                var totalPageVal = parseInt($("#rowCount").val());
                if (currpageVal >= totalPageVal) {
                    return false;
                }else{
                searchEntity.currpage  = Number($("#currpage").val())+1;
                $("#currpage").val(searchEntity.currpage);
                ajaxCommonSearch(url_,searchEntity);
                 }
            });

            $("#lastPage").click(function() {
                var currpageVal = parseInt($("#currpage").val());
                var totalPageVal = parseInt($("#rowCont").val());
                if (currpageVal >= totalPageVal) {
                    return false;
                }else{
                    searchEntity.currpage = $("#rowCount").val();
                    $("#currpage").val(searchEntity.currpage);
                    ajaxCommonSearch(url_,searchEntity);
                }
            });

            $("#pagesize").change(function(){
            	var pagesize = $('#pagesize').val();
                searchEntity.pagesize = pagesize;
                 ajaxCommonSearch(url_,searchEntity);
            });
        },
    	error : function(e) {
    		   _util.cmsTip("加载数据列表失败，请稍后重试");
		}
    });
}

function errorMsg(){
    $.alert({
        title: 'Alert!',
        content: 'Simple alert!',
        confirm: function(){
            $.alert('Confirmed!'); // shorthand.
        }
    });
}

