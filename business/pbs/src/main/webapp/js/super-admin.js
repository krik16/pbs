/**
 */
var importfilediv = null;
$(document).ready(function() {
	importfilediv = $('.shopNumberBox').html();
	// 绑定click事件
	$('.init-mall').off().on({
		'click' : function(ev) {
			var url = $(this).attr('url');
			var mongoMallId = $('.mall-id').val();
			$(this).html("初始化中...");
			$.ajax({
				url: url,
				type: 'POST',
				data:{mongoMallId:mongoMallId},
//				dataType: 'json',
				success: function(data){
					$('.init-mall').html("初始商场(含店铺)");
					$('.mall-id').val("");
					_util.cmsTip(data);
					return;
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					_util.cmsTip('系统报错！！！');
					$('.mall-id').val("");
					$('.init-mall').html("初始商场(含店铺)");
	            }
			});
			return false;
		}
	});
	//活动初始
	$('.init-mall-activities').off().on({
		'click' : function(ev) {
			var url = $(this).attr('url');
			var mongoMallId = $('.mall-id').val();
			$(this).html("初始化中...");
			$.ajax({
				url: url,
				type: 'POST',
				data:{mongoMallId:mongoMallId},
//				dataType: 'json',
				success: function(data){
					$('.init-mall-activities').html("初始活动");
					$('.mall-id').val("");
					_util.cmsTip(data);
					return;
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					_util.cmsTip('系统报错！！！');
					$('.mall-id').val("");
					$('.init-mall-activities').html("初始活动");
	            }
			});
			return false;
		}
	});
	
	$("a[id^='init']").click(function(){
		var href = $(this).attr("clickHref");
		if(href != "clicked"){
			$(this).attr("clickHref","clicked");
			$(this).html( $(this).html() + "中..." );
			window.location.href = href;
		}
	});
	
	$('#initShopNumber').off().on({//铺位号初始化弹层
		'click':function( ev ){
			popShopNumber();
		}
	});
});
function popShopNumber(){
	var content_ = "<div class='popShopNumberBox' style='padding: 33px 60px 0px 60px;'>" + importfilediv + "</div>";
	$(".shopNumberBox").remove();
	var x = $.layer({
		type: 1,
		title: "铺位号初始化",
		closeBtn: [0, true],
		border : [5, 0.5, '#666', true],
		offset: ['100px',''],
		move: ['.juanmove', true],
		area: ['500px','250px'],
		page : {
			html : content_
		},
		success : function() {
			$('.popShopNumberBox').find('#uploadButton').off().on({
				'click':function( ev ){
				  var importfileVal = $('.popShopNumberBox').find("#importfile").val();
				  if(importfileVal == ""){
					  alert('上传文件为空!!!');
					  return ;
				  }else{
					  var k = importfileVal.substr(importfileVal.indexOf("."));
					  if(k!=".xlsx"){
						  alert('上传文件类型错误');
					  }else{
						  $('.popShopNumberBox').find("#uploadButton").attr("disabled",true);
						  //$('.popShopNumberBox').find("#importfile").attr("disabled",true);
						  $('.popShopNumberBox').find("#uploadButton").text("请稍等。。。");
						  var fileId = 'importfile';
						  var paramsJson = {};
						  paramsJson['uploadType'] = "shopNumber";
						  $.ajaxFileUpload({
						        //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)  
						    	url:'../fileUpload/initExcelfile?paramsJson=' + JSON.stringify(paramsJson),
						        secureuri:false,                           //是否启用安全提交,默认为false   
						        fileElementId:fileId,               //文件选择框的id属性  
						        dataType:'json',                           //服务器返回的格式,可以是json或xml等  
						        success:function(data, status){            //服务器响应成功时的处理函数
						        	$('.popShopNumberBox').find("#uploadButton").attr("disabled",false);
						        	//$('.popShopNumberBox').find("#importfile").attr("disabled",false);
						        	$('.popShopNumberBox').find("#uploadButton").text("上传");
						        	if(data != null && data.status == '1'){
						        		alert("初始化完成！");
						        	}else{
						        		alert("初始化失败，请稍后重试！");
						        	}
									return;
						        },  
						        error:function(data, status, e){ //服务器响应失败时的处理函数  
						        	alert("文件上传失败，请重试！");
						        }
						    }); 
					  }
				  }
				}
			});
			$('.popShopNumberBox').find('#closeShopNumberBox').off().on({
				'click':function( ev ){
					layer.close(x);
				}
			});
		}
	});
}

