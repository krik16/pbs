
var _util={
	SHOW_IMG_PATH : "/bossImg",//这个要跟常量里面Constant.PicPath.rootPath一起修改
	/**
	 * 公用上传
	 * @param fileId
	 */
	ajaxFileUploadPublic:function(fileId, paramsJson, fn){  
		if ($('#'+fileId).val().length > 0) {
			//开始上传文件时显示一个图片,文件上传完成将图片隐藏  
		    //$("#loading").ajaxStart(function(){$(this).show();}).ajaxComplete(function(){$(this).hide();});  
		    //执行上传文件操作的函数  
		    $.ajaxFileUpload({
		        //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)  
		    	url:'../fileUpload/file?paramsJson='+JSON.stringify(paramsJson),
		        secureuri:false,                           //是否启用安全提交,默认为false   
		        fileElementId:fileId,               //文件选择框的id属性  
		        dataType:'json',                           //服务器返回的格式,可以是json或xml等  
		        success:function(data, status){            //服务器响应成功时的处理函数
		        	if(data.status == 1) {
	            		fn && fn(data);
						return;
	            	}else {
	            		_util.cmsTip(data.msg);
	            	}
		        },  
		        error:function(data, status, e){ //服务器响应失败时的处理函数  
		        	_util.cmsTip("图片上传失败，请重试！");
		        }  
		    }); 
		}else {
			_util.cmsTip('请先选择图片！');
		}
	},
	ajaxSubmitForm:function(formId,fn) {
//		_util.cmsTip(">>>"+$('#'+formId).serialize());
		$.ajax({
            type: "POST",
            url:$('#'+formId).attr('action'),
            data:$('#'+formId).serialize(),// 你的formid
            dataType:'json',
            error: function(request) {
                _util.cmsTip("something error");
            },
            success: function(data) {
            	if(data.status == 1) {
            		fn && fn(data);
					return;
            	}else {
            		_util.cmsTip(data.msg);
            	}
            }
        });
	},
	ajaxSubmit:function(url_, paramsJson_, fn) {//异步提交数据
		//在url后面加个时间戳,防止IE8缓存
		paramsJson_['timeStamp_'] = new Date().getTime();
		$.ajax({
			url: url_,
			type: 'POST',
			data:{paramsJson:JSON.stringify(paramsJson_)},
			dataType: 'json',
			success: function(data){
				fn && fn(data);
				return;
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
                _util.cmsTip('系统报错！！！');
            }
		});
	},
	// JavaScript 构建一个 form  
	createForm:function(action_, paramsJson_, target_) {
	    // 创建一个 form  
	    var form1 = document.createElement("form");  
	    form1.id = "common_form1";  
	    form1.name = "common_form1";  
	    // 添加到 body 中  
	    document.body.appendChild(form1);  
	    // 创建一个输入  
	    var input = document.createElement("input");  
	    // 设置相应参数  
	    input.type = "hidden";  
	    input.name = "paramsJson";  
	    input.value = paramsJson_;  
	    // 将该输入框插入到 form 中  
	    form1.appendChild(input);  
	    // form 的提交方式  
	    form1.method = "POST";  
	    // form 提交路径  
	    form1.action = action_;  
	    form1.target = target_; 
	    // 对该 form 执行提交  
	    form1.submit();  
	    // 删除该 form  
	    document.body.removeChild(form1);  
	},
	stringLength:function(str){//判断中文字符长度
		var len=0; 
		for(var i=0;i<str.length;i++){
			if(str.charAt(i)>'~'){len+=2;}else{len++;}
		}
		return len; 
	},
	confirm:function(msg,fn) {//确认提醒框
		var i_ = $.layer({
		    shade: [0],
		    area: ['auto','auto'],
		    dialog: {
		        msg: msg,
		        btns: 2,                    
		        type: 4,
		        btn: ['确认','取消'],
		        yes: function(){
		        	fn && fn(i_);
		        }, no: function(){
		        	layer.close(i_);
		        }
		    }
		});
	},
	scrollToBottom:function() {
		_util.mouseMovie2List();//要在三个地方加，现在这里比较方便；
		//滚动到最底部,后来去掉不要了
//		var bottom = document.body.scrollHeight;
//		document.body.scrollTop = bottom;
//		document.documentElement.scrollTop = bottom;
	},
	popOneLineOneNameWin:function(title_, text, fn) {//弹框，一行一个
		var layer_oneLineOneName = $.layer({
			  type: 1,
			  title: title_,
			  closeBtn: [0, true],
			  border : [5, 0.5, '#666', true],
			  offset: ['100px',''],
			  move: ['.juanmove', true],
			  area: ['400px','500px'],
			  page: {
				  html: "<div class='pop-one-line-one-name'>"+$('.pop-one-line-one-name-div').html()+"</div>"
			  },
			  end:function() {
				  _util.ifShowPopWin = 0;
			  }, 
			  success: function(){
				  _util.ifShowPopWin = 1;
				  $(".pop-one-line-one-name").find('.textarea').focus();
				  $(".pop-one-line-one-name").find('.textarea').val(text);
				  $('.pop-one-line-one-name').find('.cancelPop').click(function() {
					  _util.ifShowPopWin = 0;
					  layer.close(layer_oneLineOneName);
				  });
				  $('.pop-one-line-one-name').find('.savePop').click(function() {
					  var txt = $(".pop-one-line-one-name").find('.textarea').val();
					  var arr = txt.split('\n');
					  var tt = "";
					  var tmp = "|";
					  for(var i=0; i<arr.length; i++) {
						  if($.trim(arr[i]) != '' && tmp.indexOf('|'+$.trim(arr[i])+'|')==-1) {							  
							  tt += "<div class=\"input-item-tags-click-add input-item-tags\">"+$.trim(arr[i])+"</div>";
							  tmp += $.trim(arr[i]) + '|';
						  }
					  }
					  tmp = '';
					  if($.trim(tt) == '') {
						  tt = "<div class=\"input-item-tags-click-add\">点击添加</div>";
					  }
					  fn && fn(tt);
					  _util.ifShowPopWin = 0;
					  layer.close(layer_oneLineOneName);
				  });
			  }
		  });
	},
	mouseMovie2List:function() {
		//鼠标移上去显示提示
		$('.table-td').die().live({
			mouseenter:function(ev){
				ev.stopPropagation();
				var sProble = '';
				if($(this).attr("desc") != null && $.trim($(this).attr("desc")) != "") {	
					var len_ = (_util.stringLength($(this).attr("desc")) * 6.5 +5) *10 /10;
					sProble="<li style='min-width:"+len_+"px;'>"+$(this).attr("desc")+"</li>";
					_util.showMsn(ev,sProble);
				}
			},
			mouseleave:function(ev){
				ev.stopPropagation();
				$('#problemWarp').hide();
			}	
		});
	},
	//鼠标移入表格图标显示
	showMsn:function(ev,data){
		var oProble = $('#problemWarp');
		var l = ev.pageX+10;
		var t = ev.pageY+10;
		if(oProble.length > 0){
			$('#problemWarp').html(data).css({
				'left':l+'px',
				'top':t+'px',
				'display':'block'	
			});	
		}else{
			var oUl = $('<ul id="problemWarp" style="left:'+l+'px; top:'+t+'px">'+data+'</ul>');
			$('body').append(oUl);	
		}
	},
	//使用在弹出窗口的时候不调用回车保存的功能
	ifShowPopWin : 0,
	//判断加载图片
	loadImage:function(url, fn) {     
		var img = new Image(); //创建一个Image对象，实现图片的预下载     
		img.src = url; 
		if(img.complete) { // 如果图片已经存在于浏览器缓存，直接调用回调函数         
//			callback.call(img);  
			fn && fn();
			return; // 直接返回，不用再处理onload事件     
		}     
		img.onload = function () { //图片下载完毕时异步调用callback函数。         
//			callback.call(img);//将回调函数的this替换为Image对象
			fn && fn();
		};
	},
	saveOkTip:function() {//保存成功之后弹框提醒并自动关闭
		var lt = layer.alert('保存成功',9 );
		setTimeout(function(){layer.close(lt)},800);
	},
	ajaxSubmitAsync:function(url_, paramsJson_, fn) {//同步提交数据
		//在url后面加个时间戳,防止IE8缓存
		paramsJson_['timeStamp_'] = new Date().getTime();
		$.ajax({
			url: url_,
			type: 'POST',
			data:{paramsJson:JSON.stringify(paramsJson_)},
			dataType: 'json',
			async: false,
			success: function(data){
				fn && fn(data);
				return;
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
                _util.cmsTip('系统报错！！！');
            }
		});
	},
	ajaxSubmitForm2:function(formId,fn) {
		$.ajax({
            type: "POST",
            url:$('#'+formId).attr('action'),
            data:$('#'+formId).serialize(),// 你的formid
            dataType:'json',
            error: function(request) {
                _util.cmsTip("something error");
            },
            success: function(data) {
            	fn && fn(data);
				return;
            }
        });
	},
	cmsConfirm:function(msg, fn) {
		var bh = $("body").height(); 
	    var bw = $("body").width();
	    $(".MsgBoxConfirm .MsgBoxMain p").html(msg);
	    $(".MsgBoxBg").css({
	        height:bh, 
	        width:bw
	    }).show(); 
	    $(".MsgBoxConfirm").fadeIn(function(){
	    	$(".MsgBoxConfirm .MsgBoxMain .cancel").click(function(){	    		
	    		$(".MsgBoxBg,.MsgBoxConfirm").fadeOut(function(){
	    			$(".MsgBoxConfirm .MsgBoxMain p").html("");
	    		});
	    	});
	    	$("#MsgBoxBtn").off().on({
				'click':function(ev){
		    		fn && fn();
		    		$(".MsgBoxBg,.MsgBoxConfirm").fadeOut(function(){
		    			$(".MsgBoxConfirm .MsgBoxMain p").html("");
		    		});
				}
	    	});
	    });
	},
	cmsTip:function(msg) {
		var bh = $("body").height(); 
	    var bw = $("body").width();
		$(".MsgBoxTip .MsgBoxMain p").html(msg);
		$(".MsgBoxBg").css({
			height:bh, 
			width:bw
		}).fadeIn(); 
	    $(".MsgBoxTip").fadeIn(); 
		
		$(".MsgBoxTip").fadeIn(function(){
			$("#MsgBoxTipBtn").click(function(){	
				$(".MsgBoxBgTip").hide();
				$(".MsgBoxBg,.MsgBoxTip").fadeOut(function(){
//					$(".MsgBoxTip .MsgBoxMain p").html("");
				});
			});
		});
	},
	ajaxSubmitTimeout:function(url_, paramsJson_, fn) {//异步提交数据
		//在url后面加个时间戳,防止IE8缓存
		paramsJson_['timeStamp_'] = new Date().getTime();
		var ajaxTimeoutTest = $.ajax({
			url: url_,
			type: 'POST',
			data:{paramsJson:JSON.stringify(paramsJson_)},
			dataType: 'json',
			timeout : 1800000, //超时时间设置，单位毫秒,半小时
			success: function(data){
				fn && fn(data);
				return;
			},
            complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
        		if(status=='timeout'){//超时,status还有success,error等值的情况
        			ajaxTimeoutTest.abort();
        			_util.cmsTip("请求超时");
        		}
        	}
		});
	},
	/**
	 * 公用上传
	 * @param fileId
	 */
	ajaxFileUploadPublic2:function(fileId, paramsJson, fn){  
		if ($('#'+fileId).val().length > 0) {
			//开始上传文件时显示一个图片,文件上传完成将图片隐藏  
		    //$("#loading").ajaxStart(function(){$(this).show();}).ajaxComplete(function(){$(this).hide();});  
		    //执行上传文件操作的函数  
		    $.ajaxFileUpload({
		        //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)  
		    	url:'../fileUpload/file?paramsJson='+JSON.stringify(paramsJson),
		        secureuri:false,                           //是否启用安全提交,默认为false   
		        fileElementId:fileId,               //文件选择框的id属性  
		        dataType:'json',                           //服务器返回的格式,可以是json或xml等  
		        success:function(data, status){            //服务器响应成功时的处理函数
            		fn && fn(data);
					return;
		        },  
		        error:function(data, status, e){ //服务器响应失败时的处理函数  
		        	_util.cmsTip("图片上传失败，请重试！");
		        }  
		    }); 
		}else {
			_util.cmsTip('请先选择图片！');
		}
	},
}