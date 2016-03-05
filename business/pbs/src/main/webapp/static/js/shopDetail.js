var shopDetail = shopDetail || {};
shopDetail = {
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
			
			length = shopDetail.shopVos.length;
			divisibleVal = parseInt(shopDetail.shopVos.length / shopDetail.pageSize);
			if(length % shopDetail.pageSize != 0){
				divisibleVal += 1;
			}
			for(var i=0; i<divisibleVal; i++){
				pageInfo = shopDetail.getPageInfo(i);
				if(pageInfo && pageInfo.length > 0){
					slideHtml += '<div class="swiper-slide">';
					slideHtml += '<ul class="like-pic">';
					for(var j=0;j<pageInfo.length; j++){
						slideHtml += '<li><a href="' + shopDetail.getWebRootPath() + '/shopDetail/index.htm?id=' + pageInfo[j].id + '"><img src="' + pageInfo[j].icon + '"/></a></li>';
					}
					slideHtml += '</ul></div>';
				}
			}
			$(".swiper-wrapper").append(slideHtml);
		},
		
		init: function(){
			shopDetail.shopVos = $.parseJSON($("#shopVos").val());
		},
		
		/**
		 * 获取分页信息
		 */
		getPageInfo: function(currentPage){
			if(currentPage * shopDetail.pageSize + shopDetail.pageSize > shopDetail.shopVos.length){
				return shopDetail.shopVos.slice(currentPage * shopDetail.pageSize);
			}else{
				return shopDetail.shopVos.slice(currentPage * shopDetail.pageSize,currentPage * shopDetail.pageSize+shopDetail.pageSize);
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
	shopDetail.init();
	shopDetail.getShopVo();
	
   var mySwiper = new Swiper('.swiper-container',{
    mode:'horizontal',
    loop: true 
   });
});