$.ajaxSetup({
    cache: false
});
function ajaxsearchPayManager() {
    var url_ = "../payManager/list";
    var currpage = $('#currpage').val();
    var payNo = $("#payNo").val();
    var orderNum = $("#orderNum").val();
    var status = $("#status").val();
    var tradeType = $("#tradeType").val();
    var paramsJson_ = {
        "payNo": payNo,
        "orderNum": orderNum,
        "status": status,
        "tradeType": tradeType,
        "currpage": currpage
    };
    $.ajax({
        url: url_,
        type: 'get',
        data: {
            "paramsJson": JSON.stringify(paramsJson_)
        },
        success: function(data) {
            $("#result").html(data);
            $("#upPage").click(function() {
                var currpageVal = parseInt($("#currpage").val());
                if (currpageVal <= 1) {
                    return false;
                } else {
                    $("#currpage").val(currpageVal - 1);
                }
                $("#jumpPage").val("");
                ajaxsearchPayManager();
            });

            $("#downPage").click(function() {
                var currpageVal = parseInt($("#currpage").val());
                var totalPageVal = parseInt($("#rowCont").val());
                if (currpageVal >= totalPageVal) {
                    return false;
                } else {
                    $("#currpage").val(currpageVal + 1);
                }
                $("#jumpPage").val("");
                ajaxsearchPayManager();
            });

            $("#gopage").click(function() {
                var temppage = parseInt($('#jumpPageIn').val()),
                totalPage = parseInt($("#rowCont").val());
                if (temppage != "" && temppage != undefined && !isNaN(temppage)) {
                    if (temppage >= 1 && temppage <= totalPage) {
                        $("#currpage").val(temppage);
                        ajaxsearchPayManager();
                    } else if (temppage > totalPage) {
                        temppage = totalPage;
                        $("#jumpPage").val(totalPage);
                        $("#currpage").val(totalPage);
                        ajaxsearchPayManager();
                    } else {
                        _util.cmsTip("请输入大于0的数字!");
                    }
                } else if (temppage == 0) {
                    temppage = 1;
                    $("#jumpPage").val(1);
                    $("#currpage").val(1);
                    ajaxsearchPayManager();
                } else {
                    _util.cmsTip("请输入数字!");
                }
            });
        }
    });
}

$(document).ready(function() {
	ajaxsearchPayManager();
	$("#search-button").click(function() {
		$("#currpage").val("1");
		ajaxsearchPayManager();
	});

});

function keypressEvent(event) {
    if (event.keyCode == "13") {
        ajaxsearchPayManager();
    }
};

function checkAll() { // 全选or取消全选;
    if ($("#checkAll").attr("checked") == 'checked') {
        $('input[name="sub_check"]').attr("checked", true);
    } else {
        $('input[name="sub_check"]').attr("checked", false);
    }
}

function batchPay(type) { // 批量操作
    var ids = new String();
    var validity = true;
    $('input[name="sub_check"]').each(function() {
        if ($(this).attr("checked")) {
            var value = $(this).val();
            var array = value.split("/");
            ids += "," + array[0];
            if (array[1] != type) {
                if (type == 1) {
                    alert('您选择的数据中包含非退款项！');
                } else {
                    alert('您选择的数据中包含非付款项！');
                }
                validity = false;
                return false;
            }
        }
    });
    if (!validity) return;
    if (ids == '') {
        alert('您未选择任何数据！');
        return;
    }
    var url = '../v5/PCWebPageAlipay/zhifubaoBatchPay.htm?ids=' + ids + '&type=' + type;
    window.open(url);
}