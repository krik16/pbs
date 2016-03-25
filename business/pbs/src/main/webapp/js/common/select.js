
/*
* url 数据请求地址
* parentId 父数据id
* eId 子控件id
*
* */
function selectChange(url,parentId,eId){
    var pid = document.getElementById(parentId);
    var sid = document.getElementById(eId);
    //清除原有选项
    sid.options.length = 1;
    $.post(url, {
        parentId : pid.value
    }, function(data) {
        if(data.meta.errno != 0){
            Modal.alert({
                msg: "操作失败,"+data.meta.msg
            });
            return;
        }
        var list = data.result.data;
        for (var i=0;i<list.length;i++){
            var opp = new Option(list[i].name,list[i].id);
            sid.add(opp);
        }
    }, "json");
}