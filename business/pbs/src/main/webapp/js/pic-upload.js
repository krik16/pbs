var shopimgid = 0;
$(document).ready(function() {
	_upload_img.init();
	var imagediv = $(".show_shop_img").find("div[id^='shopImgDiv_']");
	$(imagediv).each(function() {
		shopimgid++;
	});
	if(shopimgid==4){
		$("#shopImg_click").hide();
	}
});

_upload_img = {
	init : function() {// 点击图片弹出上传对话框
		$('.upload_photo_icon').off().on({
			'click' : function(ev) {
				$("#myfiles").click();
				return false;
			}
		});
		// 选择图片之后马上触发上传事件
		$('#myfiles').die().live({
			'change' : function(ev) {
				_upload_img.uploadPhotoIcon();
				return false;
			}
		});
	},
	uploadPhotoIcon : function() {
		var fieldId = 'myfiles';
		var picPath = '/pic/';// 上传的目录
		var maxSize = '102400000';// 1024*1024=1M
//		var picType = '.jpg';
		var paramsJson = {};
		paramsJson['picPath'] = picPath;
		paramsJson['maxSize'] = maxSize;
//		paramsJson['picType'] = picType;
		
		_util.ajaxFileUploadPublic(fieldId,paramsJson,function(data) {
//			var imageVal = $(".show_shop_img").find("div[id^='shopImgDiv_']").find(".pic").find("img");
//			var nowimagecount = 0;
//			if (imageVal != "" && typeof (imageVal) != 'undefined') {
//				$(imageVal).each(function() {
//					nowimagecount++;
//				});
//			}
//			shopimgid = nowimagecount;
//			var showIMG = $("#imageshow").html();
//			// alert(showIMG);
//			var showImgae = "<div class='shopsImg' id='shopImgDiv_"+ shopimgid + "'>" + showIMG + "</div>";
//			$(".show_shop_img").append(showImgae);
//			$(".show_shop_img").find("#shopImgDiv_" + shopimgid).find('img').attr('src',_util.SHOW_IMG_PATH + picPath+ data.fileName);
//			$(".show_shop_img").find("#shopImgDiv_" + shopimgid).find('img').attr('fileName',data.fileName);
//			$('.show_shop_img').find("#shopImgDiv_" + shopimgid).find('img').off('click');
//			shopimgid++;
//			if(shopimgid==4){
//				$("#shopImg_click").hide();
//			}
//			_upload_img.sortPhotos();
			if(data.status == 1){
				$("#filename").val(data.fileName);
//				$("#filesize").val(data.fileSize);
				var upload_img = document.getElementById("upload_img");
//				var ctxValue = $("#ctxValue").val();
				upload_img.src = "../../"+data.fileName;
				_util.cmsTip("上传成功！！");
			}else{
				$("#filename").val("");
				$("#filesize").val("");
				_util.cmsTip("上传失败！！");
			}
		});
	}
};

