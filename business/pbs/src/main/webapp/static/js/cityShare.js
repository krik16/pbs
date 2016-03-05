var cityShare = cityShare || {};

cityShare = {
	/** 页面初始化方法 */
	init : function() {
		setInterval(cityShare.auto, 4000);
		
		/**
		 * 添加click事件
		 */
		$(".activity-fix-btn").bind('click', function(){
			window.location.href = "http://www.rongyi.com/product/app.htm";
		});
	},

	show : function(num) {
		var pics = $(".city-shuffling ul li");
		pics.eq(num).show();
		pics.fadeOut(300);
		pics.eq(num).hide();
		pics.eq(num).fadeIn(300);
	},

	auto : function() {
		var len=$(".city-shuffling li").length,
	    	index=0;
		if (index == len - 1) {
			index = 0;
		} else {
			index += 1;
		}
		cityShare.show(index);
	}
};
$(document).ready(function() {
	cityShare.init();
});