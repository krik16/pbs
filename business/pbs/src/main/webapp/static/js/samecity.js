var sameCity = sameCity || {};
sameCity = {
		shopVos: null,
		
		/** 页面显示条数 */
		pageSize: 4,
		
		/**
		 * 你可能喜欢的
		 * <div class="swiper-slide"> 
		         <ul class="like-pic">
		          <li><img src="${rc.contextPath}/static/img/brand.png"/></li>
		          <li><img src="${rc.contextPath}/static/img/brand.png"/></li>
		          <li><img src="${rc.contextPath}/static/img/brand.png"/></li>
		          <li><img src="${rc.contextPath}/static/img/brand.png"/></li>
		        </ul>
	      	 </div>      
		 */
		getShopVo: function(){
			var slideHtml = "", length, divisibleVal, pageInfo=[];
			
			length = sameCity.shopVos.length;
			divisibleVal = parseInt(sameCity.shopVos.length / sameCity.pageSize);
			if(length % sameCity.pageSize != 0){
				divisibleVal += 1;
			}
			for(var i=0; i<divisibleVal; i++){
				pageInfo = sameCity.getPageInfo(i);
				if(pageInfo && pageInfo.length > 0){
					slideHtml += '<div class="swiper-slide">';
					slideHtml += '<ul class="like-pic">';
					for(var j=0;j<pageInfo.length; j++){
						var temp= pageInfo[j].headImg
						if(temp!=''){
							slideHtml += '<li><a href="javascript:void(0)"><img src="' + pageInfo[j].headImg + '"/></a></li>';
						}else{
							slideHtml += '<li><a href="javascript:void(0)"><img src="/app/static/img/my_user.png"/></a></li>';
						}
					}
					slideHtml += '</ul></div>';
				}
			}
			$(".swiper-wrapper").append(slideHtml);
		},
		
		init: function(){
			sameCity.shopVos = $.parseJSON($("#shopVos").val());
		},
		
		/**
		 * 获取分页信息
		 */
		getPageInfo: function(currentPage){
			if(currentPage * sameCity.pageSize + sameCity.pageSize > sameCity.shopVos.length){
				return sameCity.shopVos.slice(currentPage * sameCity.pageSize);
			}else{
				return sameCity.shopVos.slice(currentPage * sameCity.pageSize,currentPage * sameCity.pageSize+sameCity.pageSize);
			}
		},
		
		getWebRootPath: function(){
		    var webroot=document.location.href;
		    webroot=webroot.substring(webroot.indexOf('//')+2,webroot.length);
		    webroot=webroot.substring(webroot.indexOf('/')+1,webroot.length);
		    webroot=webroot.substring(0,webroot.indexOf('/'));
		    var rootpath="/"+webroot;
		    return rootpath;
		}
};

$(document).ready(function(){
	var imglist = $('img');
	var size = imglist.size();
	for (var i = 0; i < size; i++) {
		imglist.eq(i).removeAttr('style');
		imglist.eq(i).css('max-width', '100%');
	}
	sameCity.init();
	sameCity.getShopVo();
	
   var mySwiper = new Swiper('.swiper-container',{
    mode:'horizontal',
    loop: true 
   });
});