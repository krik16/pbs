var marketDetail = marketDetail || {};

marketDetail = $.extend(marketDetail, {
	init: function(){
		var floors, floor, isDefault = true, yhUlHtml;
		
		floors = $.parseJSON($("#floor").val());
		
		/**
		 * <ul class="yh-ul change-ul">
        <li>
          <div class="yh-wrap floor-wrap">
            <a href="#" class="floor-info">
              <div class="floor-left">
               <span class="floor-num">A</span>区
              </div>
              <div class="floor-right">
                <h3>美妆 鞋子</h3>
                <p>达芙妮、达芙妮、达芙妮、达芙妮、达芙妮、达芙妮、达芙妮、达芙妮、达芙妮、达芙妮、达芙妮、达芙妮、达芙妮、达芙妮、</p>
              </div>
            </a>
          </div>
        </li>
      </ul>
		 */
		
		for(var key in floors){
			if("empty" == key){
				$(".search-floor").hide();
				$("#select").hide();
				$(".option").hide();
			}else{
				$(".search-floor").show();
				$("#select").show();
				$(".option").find("ul").append('<li tip="' + key + '">' + key + '</li>');
			}
			
			yhUlHtml = '<ul class="yh-ul change-ul" id="floor' + key + '">';
			
			for(var i=0; i< floors[key].length; i++){
				if(isDefault){
					$("#buildingArea").text(key);
				}
				floor = floors[key][i];
				yhUlHtml += '<li>';
				yhUlHtml += '<div class="yh-wrap floor-wrap">';
				yhUlHtml += '<a href="' + marketDetail.getWebRootPath() + '/v5/mall/share-floorMallList.htm?floorId=' + floor.id + '&mall_id=' + floor.mall_id + '" class="floor-info">';
				yhUlHtml += '<div class="floor-left">';
				yhUlHtml += '<span class="floor-num">' + floor.name.substring(0, floor.name.length - 1) + '</span>' + floor.name.substring(floor.name.length - 1, floor.name.length);
				yhUlHtml += '</div>';
				yhUlHtml += '<div class="floor-right">';
				yhUlHtml += '<h3>' + floor.show_cate + '</h3>';
				yhUlHtml += '<p>' + floor.show_shop + '</p>';
				yhUlHtml += '</div></a></div></li>';
			}
			yhUlHtml += "</ul>";
			$("#buildingNav").append(yhUlHtml);
			isDefault = false;
		}
	},
	
	bindAction: function(){
		// 楼层导航
	   $("#select").mousedown(function(){
	    $(".option").fadeIn();
	   });
		
		$(".option li").each(function(i){
		     $(this).mousedown(function(){
		       $(this).addClass("current").siblings().removeClass("current");
		       $("#select em").text($(this).text());
		       $(".option").fadeOut();
		       $(".change-ul").hide();
		       $("#floor" + $(this).text()).show();
		     });
	   });
	},
	
	getWebRootPath: function(){
	    var webroot=document.location.href;
	    webroot=webroot.substring(webroot.indexOf('//')+2,webroot.length);
	    webroot=webroot.substring(webroot.indexOf('/')+1,webroot.length);
	    webroot=webroot.substring(0,webroot.indexOf('/'));
	    var rootpath="/"+webroot;
	    return rootpath;
	}
});
$(document).ready(function(){
	marketDetail.init();
	marketDetail.bindAction();
	$("#floor" + $("#buildingArea").text()).show();
});