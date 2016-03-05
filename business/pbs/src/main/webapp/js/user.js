$.ajaxSetup({ cache: false });
/**
 * 账号管理
 */
$(document).ready(function() {
	// 绑定click事件
	eventActionUtil.clickAction(users);
	_user.init();
	_user.search();
//	initMallName();
});
_user = {
	init : function() {
		$('.search-button').off().on({
			'click' : function(ev) {
				_user.search();
				return false;
			}
		});
	},
	search : function(currentPage) {
		if (typeof (currentPage) == 'undefined' || currentPage == ""
				|| currentPage == null) {
			currentPage = 1;
		}
		var mallName = $('.search-input').val();
		var url_ = "../user/list";
		var paramsJson_ = {};
		$(".search_result_list").load(url_, {
			mallName : encodeURIComponent(mallName),
			currentPage : encodeURIComponent(currentPage)
		}, function() {

		});
	}
}

var users = {

	/** 校验规则 */
	validateRules : {
		mallId : {
			label : '选择商场'
		},
		role : {
			label : '用户权限'
		},
		account : {
			label : '账户名称'
		},
		password : {
			label : '账户密码'
		},
	},

	/** 验证表单 */
	validateForm : function() {
		var fieldValue, validateRule, formData = users.getPageForm().jqForm(
				"getValue"), validateRules = users.validateRules;
		for ( var fieldName in validateRules) {
			fieldValue = formData[fieldName];
			if(fieldName == "mallId" || fieldName  =="role"){
				if(fieldValue ==-1){
					$("#confirmUser").html("保存");
		    		$("#confirmUser").attr("clickAction", "save");
		    		validateRule = validateRules[fieldName];
		    		return validateRule.label + "不能为空";
				}
			}
			validateRule = validateRules[fieldName];
			if ($.trim(fieldValue).length == 0) {
				return validateRule.label + "不能为空";
			}
		}
		return "";
	},

	/** 获取页面表单对象 */
	getPageForm : function() {
		return $("#boxForm").jqForm({});
	},
	/**
	 * 获取dialog中的表单对象
	 */
	getCheckBoxForm : function() {
		return $("#boxForm").jqForm({});
	},
	/** 保存 */
	save : function() {
		$("#confirmUser").html("保存中...").removeAttr("clickAction");
		var validateResult = users.validateForm();
		var _url = '../user/save';
		var formData = users.getPageForm().jqForm("getValue");

		 if ($.trim(validateResult).length != 0) {
		 layer.alert(validateResult,8);
		 $("#confirmUser").html("保存");
		 $("#confirmUser").attr("clickAction", "save");
		 return;
		 }
		$.ajax({
			url : _url,
			type : 'POST',
			data : {
				paramsJson : JSON.stringify(formData),
			},
			dataType : 'json',
			success : function(data) {
				$("#confirmUser").html("保存");
				$("#confirmUser").attr("clickAction", "save");
				if (data != null && data.Success != null && data.userNameHasUsed == null) {
					alert("保存成功");
					$("input[name=id]").val(data.id);
					closeUserBox();
					
				} else if(data.userNameHasUsed != null){
					 layer.alert("此用户名已经被使用",8);
				}
				else if (data.Success != null) {
					 layer.alert("保存不成功",8);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert('系统报错！！！');
			}
		});
	},

	batchDelete : function() {

		var checklist = document.getElementsByName("selected");
		var arrayObj = new Array();
		for (var i = 0; i < checklist.length; i++) {
			if (checklist[i].checked == 1) {
				arrayObj.push(checklist[i].value);
			}
		}
		if(arrayObj.length == 0){
			_util.cmsTip("选择要删除的用户！");
			return;
		}
		var url = "../user/delete?ids=" + arrayObj;
		$.get(url, function(data) {
			if (data != null && data.Success != null) {
				_util.cmsTip("删除用户成功！");
				var currpageVal = parseInt($("#currpageVal").val());
				_user.search(currpageVal);
			} else {
				return;
			}

		});
	},
	/**
	 * 向上翻页
	 * 
	 * @returns {Boolean}
	 */
	upPage : function() {
		var currpageVal = parseInt($("#currpageVal").val());
		if (currpageVal <= 1) {
			return false;
		} else {
			currpageVal = currpageVal - 1;
		}
		_user.search(currpageVal);
	},
	/**
	 * 向下翻页
	 * 
	 * @returns {Boolean}
	 */
	downPage : function() {
		var currpageVal = parseInt($("#currpageVal").val());
		var totalPageVal = parseInt($("#rowCont").val());
		if (currpageVal >= totalPageVal) {
			return false;
		} else {
			currpageVal = currpageVal + 1;
		}
		_user.search(currpageVal);
	},
	gopage : function() {
		var temppage = parseInt($('#jumpPageIn').val()), totalPage = parseInt($(
				"#rowCont").val());
		if (temppage != "" && temppage != undefined && !isNaN(temppage)) {
			if (temppage >= 1 && temppage <= totalPage) {
				$('#jumpPage').val(temppage);
				_user.search(temppage);
			} else {
				_util.cmsTip("请输入大于1小于" + totalPage + "的数字!");
			}
		} else if (temppage == 0) {
			_util.cmsTip("请输入大于1小于" + totalPage + "的数字!");
		} else {
			_util.cmsTip("请输入数字!");
		}
	},
}
/**
 * 新建、修改弹出框
 */
function newUserBox() {
	initMallName();
	$("input[name=account]").attr("disabled",false);
	var userBox = $("#out-brandBox").html();
	var layer_oneLineOneName = $.layer({
		type : 1,
		title : false,
		shadeClose : false,
		fix : false,
		closeBtn : [ 1, true ],
		border : [ 5, 0.5, '#000', true ],
		offset : [ '100%', '50%' ],
		move : [ '.brandBox-title', true ],
		area : [ 'auto', 'auto' ],
		page : {
			html : userBox,
		},

		success : function() {
			$("select[name=mallId]").unbind().bind('change',function() {
				var mongoId = $(this).children('option:selected').attr('mongoId');
				console.log('mongoId>>>'+mongoId);
				$(this).next('span').html(mongoId);
			});
			$(".xubox_close").addClass("display-none");
			$(".xubox_border").addClass("display-none");
		}
	});
}
/**
 * 修改弹出框
 * 
 * @param userId
 * @param account
 * @param mallId
 * @param role
 */
function editUserBox(userId, account, mallId, role) {
	initMallName();
	var userBox = $("#out-brandBox").html();
	var layer_oneLineOneName = $
			.layer({
				type : 1,
				title : false,
				shadeClose : false,
				fix : false,
				closeBtn : [ 1, true ],
				border : [ 5, 0.5, '#000', true ],
				offset : [ '100%', '50%' ],
				move : [ '.brandBox-title', true ],
				area : [ 'auto', 'auto' ],
				page : {
					html : userBox,
				},
				success : function() {
					var mytime1 = setInterval(function(){
						var count1=$("select[name=mallId] option").length;
						var count2=$("select[name=role] option").length;
//						console.log(count1 +":"+ count2);
						if(count1>1 && count2>1) {
							clearInterval(mytime1);
							$(".xubox_close").addClass("display-none");
							$(".xubox_border").addClass("display-none");
							$("input[name=id]").val(userId);
							$("select[name=mallId]").val(mallId);
							$("select[name=role]").val(role);
							$("input[name=account]").val(account);
							if(account != null){
								$("input[name=account]").attr("disabled",true);
							}
							$("input[name=password]").val("");
							$("input[name=password]").attr("placeholder",
							"慎重修改密码，此处不填默认为不做修改");
						}
					},100);
					
				}
			});
}
/**
 * 关闭弹出框
 */
function closeUserBox() {
	var currpageVal = parseInt($("#currpageVal").val());
	$("input[name=id]").val("");
	$(".xubox_close").click();
	_user.search(currpageVal);
}
/**
 * 初始化Mall name 下拉框
 */
function initMallName() {
	var url_ = '../user/getAllMall';
	var paramsJson_ = {};
	intedFlag = false;
	_util.ajaxSubmit(url_, paramsJson_, function(data) {
		if (data != null && data.status == 1) {
			var mallSelect = $("select[name=mallId]");
			mallSelect.empty();
			var option = $("<option>").text('-请选择-').val('-1');
			mallSelect.append(option);
			$.each(data.list, function(n, value) {
				option = $("<option mongoId='"+value.mongoId+"'>").text(value.name).val(value.id);
				mallSelect.append(option);
			});
			initRoleName();
		} else {
			initMallName();
		}
	});
}

/**
 * 初始化用户权限下拉框
 */
function initRoleName() {

	var url_ = '../user/getAllRoles';
	var paramsJson_ = {};
	_util.ajaxSubmit(url_, paramsJson_, function(data) {
		if (data != null && data.status == 1) {
			var mallSelect = $("select[name=role]");
			mallSelect.empty();
			var option = $("<option>").text('-请选择-').val('-1');
			mallSelect.append(option);
			$.each(data.list, function(n, value) {
				option = $("<option>").text(value.name).val(value.id);
				mallSelect.append(option);
			});
		} else {
			initRoleName();
		}
	});
}
/**
 * checkbox 批量选择
 */
function selectAll() {
	var checklist = document.getElementsByName("selected");
	if (document.getElementById("controlAll").checked) {
		for (var i = 0; i < checklist.length; i++) {
			checklist[i].checked = 1;
		}
	} else {
		for (var j = 0; j < checklist.length; j++) {
			checklist[j].checked = 0;
		}
	}
}
/**
 * 校验原密码
 */
function verifyPassword() {
	var original_password = $("#original-password").val();
	if($.trim(original_password) == "" || typeof(original_password) == "undefined"){
		_util.cmsTip("请输入原始密码");
		return;
	}
	var url = "../user/verifyPassword";
	$.ajax({
		url : url,
		type : 'POST',
		data : {
			originalPassword : encodeURIComponent(original_password)
		},
		dataType : 'json',
		success : function(data) {
			if(data!=null && data.verify == "right"){
				resetPassword();
			}else{
				_util.cmsTip("请输入正确的原始密码");
				$("#original-password").val("");
				$("#password1").val("");
				$("#password2").val("");
			}
		}
	});
}

/**
 * 重置密码
 */
function resetPassword() {
	var password1 = $("#password1").val();
	var password2 = $("#password2").val();
	var original_password = $("#original-password").val();
	if (password1 == "" || password1 == undefined) {
		_util.cmsTip("请输入新密码");
		return;
	}
	if (password2 == "" || password2 == undefined) {
		_util.cmsTip("请重复输入密码");
		return;
	}
	if (password1 != password2) {
		_util.cmsTip("两次密码输入不一致");
		$("#password1").val("");
		$("#password2").val("");
		return;
	}
	var url = "../user/savePassword"
	var id = $("#userId").val();
	var password = $("#password1").val();
	$("#reset").html("正在重置...");
	//$("#reset").attr("onclick", "");
	$.ajax({
		url : url,
		type : 'POST',
		data : {
			password : encodeURIComponent(password),
			originalPassword : encodeURIComponent(original_password)
		},
		dataType : 'json',
		success : function(data) {
			$("#reset").html("确认重置");
			//$("#reset").attr("onclick", "resetPassword()");
			if (data != null && data.Success != null) {
				_util.cmsTip("重置成功");
				$("#original-password").val("");
				$("#password1").val("");
				$("#password2").val("");
			} else if (data.Success != null) {
				_util.cmsTip("重置不成功");
			}
		}
	});
}
$(document).ready(function() {
	$("#reset").click(function(){
		verifyPassword();
	});
})