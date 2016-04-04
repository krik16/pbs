function changePwd(){
    var oldPwd = $("#oldPwd").val();
    var newPwd1 = $("#newPwd1").val();
    var newPwd2 = $("#newPwd2").val();
    if(!oldPwd || oldPwd == ''){
        Modal.alert({
            msg: "旧密码不能为空"
        });
        return;
    }
    if(!newPwd1 ||newPwd1 == ''){
        Modal.alert({
            msg: "新密码不能为空"
        });
        return;
    }
    if(!newPwd2 || newPwd2 == ''){
        Modal.alert({
            msg: "确认密码不能为空"
        });
        return;
    }
    if(newPwd1 != newPwd2){
        Modal.alert({
            msg: "两次密码不相同"
        });
        return;
    }
    $.post("../user/saveChangePwd", {
        oldPwd : oldPwd,
        newPwd1 : newPwd1,
        newPwd2 : newPwd2
    }, function(data) {
        if(data.meta.errno != 0){
            Modal.alert({
                msg: "操作失败,"+data.meta.msg
            });
            return;
        }
        Modal.alert({
            msg: "修改密码成功"
        });
    }, "json");
}