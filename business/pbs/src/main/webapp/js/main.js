/**
 * 首页
 */
$(document).ready(function() {
	_main.init();
});
var _main = {
	init:function() {
		$('.verify_shop').off().on({
			'click':function( ev ){
				var obj_ = $(this);
				var shopId = $(this).attr('shop');
				var url_ = '../main/verifyShop';
//				console.log("shopId:"+shopId);
				var paramsJson_ = {};
				paramsJson_['shopId'] = shopId;
				var operator = $(this).attr('operator');
				paramsJson_['operator'] = operator;
				_util.ajaxSubmit(url_, paramsJson_, function(data){
					if(data.status == 1) {
						obj_.parents('tr').remove();
					}else { 
						alert(data.msg);
					}
				});
			}
		});
		$('.verify_activity').off().on({
			'click':function( ev ){
				var obj_ = $(this);
				var activityId = $(this).attr('activity');
				var url_ = '../main/verifyActivity';
				var paramsJson_ = {};
				paramsJson_['activityId'] = activityId;
				var operator = $(this).attr('operator');
				paramsJson_['operator'] = operator;
				_util.ajaxSubmit(url_, paramsJson_, function(data){
					if(data.status == 1) {
						obj_.parents('tr').remove();
					}else { 
						alert(data.msg);
					}
				});
			}
		});
		$('.verify_mallActivity').off().on({
			'click':function( ev ){
				var obj_ = $(this);
				var itemId = $(this).attr('itemId');
				var url_ = '../main/verifyMallActivity';
				var paramsJson_ = {};
				paramsJson_['itemId'] = itemId;
				var operator = $(this).attr('operator');
				paramsJson_['operator'] = operator;
				_util.ajaxSubmit(url_, paramsJson_, function(data){
					if(data.status == 1) {
						obj_.parents('tr').remove();
					}else { 
						alert(data.msg);
					}
				});
			}
		});
		$('.verify_coupons').off().on({
			'click':function( ev ){
				var obj_ = $(this);
				var itemId = $(this).attr('itemId');
				var url_ = '../main/verifyCoupons';
				var paramsJson_ = {};
				paramsJson_['itemId'] = itemId;
				var operator = $(this).attr('operator');
				paramsJson_['operator'] = operator;
				_util.ajaxSubmit(url_, paramsJson_, function(data){
					if(data.status == 1) {
						obj_.parents('tr').remove();
					}else { 
						alert(data.msg);
					}
				});
			}
		});
		$('.verify_mallActivityBatch').off().on({
			'click':function( ev ){
				var obj_ = $(this);
				var moveIds = $("#moveIds").val();
				var url_ = '../main/verifyMallActivityBatch';
				var paramsJson_ = {};
				var operator = $(this).attr('operator');
				paramsJson_['operator'] = operator;
				paramsJson_['moveIds'] = moveIds;
				_util.ajaxSubmit(url_, paramsJson_, function(data){
					if(data.status == 1) {
						$(".moveMallActivitiesTr").remove();
					}else { 
						alert(data.msg);
					}
				});
			}
		});
		//审核优惠
		$('.verify_shopNumber').off().on({
			'click':function( ev ){
				var obj_ = $(this);
				var shopNumber = $(this).attr('shopNumber');
				var url_ = '../main/verifyShopNumber';
				var paramsJson_ = {};
				paramsJson_['shopNumber'] = shopNumber;
				var operator = $(this).attr('operator');
				paramsJson_['operator'] = operator;
				_util.ajaxSubmit(url_, paramsJson_, function(data){
					if(data.status == 1) {
						obj_.parents('tr').remove();
					}else { 
						alert(data.msg);
					}
				});
			}
		});
		//地图变更审核
		$('.verify_maps').off().on({
			'click':function( ev ){
				var obj_ = $(this);
				var mapsId = $(this).attr('mapsId');
				var url_ = '../main/verifyMaps';
				var paramsJson_ = {};
				paramsJson_['mapsId'] = mapsId;
				var operator = $(this).attr('operator');
				paramsJson_['operator'] = operator;
				_util.ajaxSubmit(url_, paramsJson_, function(data){
					if(data.status == 1) {
						obj_.parents('tr').remove();
					}else { 
						alert(data.msg);
					}
				});
			}
		});
		//自定义分类审核
		$('.verify_shops_category').off().on({
			'click':function( ev ){
				var obj_ = $(this);
				var shopcat = $(this).attr('shopcat');
				var url_ = '../main/verifyShopsCategories';
				var paramsJson_ = {};
				paramsJson_['shopcat'] = shopcat;
				var operator = $(this).attr('operator');
				paramsJson_['operator'] = operator;
				_util.ajaxSubmit(url_, paramsJson_, function(data){
					if(data.status == 1) {
						obj_.parents('tr').remove();
					}else { 
						alert(data.msg);
					}
				});
			}
		});
		
		//同步
		if($('.Platform').length > 0) {
			$('.Platform').die().live({
				mouseenter:function(ev){
					ev.stopPropagation();
					$('.PlatformList').show();
				},
				mouseleave:function(ev){
					ev.stopPropagation();
					$('.PlatformList').hide();
				}	
			});
			var releaseFlag = "1";
			$('.release').off().on({
				'click':function( ev ){
					var url_ = '../release/release';
					var paramsJson_ = {};
					$(this).text('同步中');
					var obj_ = $(this);
					if(releaseFlag == "1"){
						releaseFlag = "0";
						_util.ajaxSubmitTimeout(url_, paramsJson_, function(data){
							if(data.status == 1) {
								msgBoxBg(data.msg);
							}else { 
								msgBoxBg(data.msg);
							}
							releaseFlag = "1";
							obj_.text('同步');
						});
					}
					
				}
			});
		}
		
		//审核
			$('.verify_shop_no').off().on({
				'click':function( ev ){ 
					var obj_ = $(this);
					var shopId = $(this).attr('shop');
					_main.popVerifyNot(obj_, shopId, 'shop');
				}
			});
			
			$('.verify_activities_no').off().on({
				'click':function( ev ){
					var obj_ = $(this);
					var activitiesId = $(this).attr('activity');
					_main.popVerifyNot(obj_, activitiesId, 'activity');
				}
			});
			
			$('.verify_mallActivities_no').off().on({
				'click':function( ev ){
					var obj_ = $(this);
					var mallActivitiesId = $(this).attr('itemId');
					_main.popVerifyNot(obj_, mallActivitiesId, 'mallActivity');
				}
			});
			$('.verify_coupons_no').off().on({
				'click':function( ev ){
					var obj_ = $(this);
					var couponsId = $(this).attr('itemId');
					_main.popVerifyNot(obj_, couponsId, 'coupon');
				}
			});
	},
	popVerifyNot:function(obj_,entityId,_type) {
		var content_ = "<div class='verify_no_box'>" + $(".verify-no-box").html() + "</div>";
		
		var layer_verify_no = $.layer({
			type: 1,
			title: "审核未通过理由",
			closeBtn: [0, true],
			border : [5, 0.5, '#666', true],
			offset: ['100px',''],
			move: ['.juanmove', true],
			area: ['400px','300px'],
			page : {
				html : content_
			},
			success : function() {
//				$('.verify_no_box').find('.entity_id').val(entityId);
				$('.verify_no_box').find('.save_').off().on({
					'click':function( ev ){
						var url_ = '../main/unverify';
						var paramsJson_ = {};
						var reason_ = $('.verify_no_box').find('.reason_text').val();
						if(reason_ == null || reason_.length == 0){
							alert("审核理由不能为空！！");
							return false;
						}else if($.trim(reason_).length == 0){
							alert("审核理由不能全为空格！！");
							return false;
						}
//						var obj_ = $(this);
						paramsJson_["type"] = _type;
						paramsJson_["entityId"] = entityId;
						paramsJson_["reason"] = encodeURIComponent(reason_);
						_util.ajaxSubmit(url_, paramsJson_, function(data){
							if(data.status == 1) {
								if(_type == "mallActivityMove"){
									$(".moveMallActivitiesTr").remove();
								}else{
									obj_.parents('tr').remove();
								}
							}else {
								alert(data.msg);
							}
							layer.close(layer_verify_no);
						});
					}
				});
				$('.verify_no_box').find('.cancel_').off().on({
					'click':function( ev ){
						layer.close(layer_verify_no);
					}
				});
			}
		});
	}
}