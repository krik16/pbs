

window.Rongyi = {}

if(typeof AndroidWebAppInterface != 'undefined'){
	window.Rongyi = {
		HtmlNavigatePaySuccess: function(isSuccess,msg){
			AndroidWebAppInterface.HtmlNavigatePaySuccess(isSuccess,msg);
		},		
		gotoCategory: function(id){
			AndroidWebAppInterface.HtmlNavigateToTypeList(id);
		},
		gotoActivity: function(id){
			AndroidWebAppInterface.HtmlNavigateToActivity(id);
		},
		gotoArticle: function(id){
			AndroidWebAppInterface.HtmlNavigateToSurpriseDay(id);
		},
		go: function(type){
			AndroidWebAppInterface["HtmlNavigateTo"+type]();
		},
		gotoDetail: function(type,id){
			AndroidWebAppInterface.HtmlNavigateToDetail(type,id);
		},
		getCityName: function(){
			return AndroidWebAppInterface.HtmlGetCityName();
		},
		getMap: function(type,id){
			return  AndroidWebAppInterface.HtmlNavigateToMap(type,id);
		},
		getLogin: function(){
			return  AndroidWebAppInterface.HtmlNavigateToLogin();
		},
		getshowToast: function(tag){
			return  AndroidWebAppInterface.showToast(tag);
		},
	    gotoSearchList: function(id){
	      AndroidWebAppInterface.HtmlNavigateToSearchList(id);
	    }, 
	    gotoInformationList: function(id){
	      AndroidWebAppInterface.HtmlNavigateToInformationList();
	    },
	    type: 'Android'
	} 
	Rongyi.getCityName();
}

function defineIOS(bridge){
    bridge.init(function(message, responseCallback) {
        var data = { 'Javascript Responds':'Wee!' }
        responseCallback(data)
    })

	window.Rongyi = {
    	HtmlNavigatePaySuccess : function(isSuccess,msg){
    		bridge.callHandler('HtmlNavigatePaySuccess', {'isSuccess':isSuccess,'msg':msg});
		},
    	getMap: function(type,id){
			bridge.callHandler('HtmlNavigateToMap', {'name':type,'id':id});
		},
		gotoCategory: function(id){
			bridge.callHandler('HtmlNavigateToTypeList', {'name': id});
		},
		gotoActivity: function(id){
			bridge.callHandler('HtmlNavigateToActivity', {'name': id});
		},
		gotoArticle: function(id){
			bridge.callHandler('HtmlNavigateToSurpriseDay', {'name': id});
		},
		go: function(type){
			bridge.callHandler("HtmlNavigateTo"+type);
		},
		gotoDetail: function(type,id){
			bridge.callHandler('HtmlNavigateToDetail', {'name':type,'id':id}); 
		},
		getCityName: function(){
            var citystr;
            bridge.callHandler('HtmlGetCityName', {'foo': 'bar'}, function(response) {
               citystr = response; 
            }) 
		},
		getLogin: function(tag){
			bridge.callHandler('HtmlNavigateToLogin');
		},
		getshowToast: function(tag){
			bridge.callHandler('showToast',{"tag":tag});
		},
	    gotoSearchList: function(id){
	      bridge.callHandler('HtmlNavigateToSearchList', {'name': id});
	    },
	    gotoInformationList: function(){
	      bridge.callHandler('HtmlNavigateToInformationList');
	    }, 
	    type: 'iOS'
	}
    Rongyi.getCityName();
    Rongyi.HtmlNavigatePaySuccess(true,"Success!");
}

function connectWebViewJavascriptBridge(callback) {
	if (window.WebViewJavascriptBridge) {
		callback(WebViewJavascriptBridge)
	} else {
		document.addEventListener('WebViewJavascriptBridgeReady', function() {
			callback(WebViewJavascriptBridge)
		}, false)
	}
}

connectWebViewJavascriptBridge(defineIOS)

$(function(){
	$(".vote li span.radio").each(function(i){
		$(this).click(function(){
			$(".vote li span.radio").removeClass("check").eq(i).addClass(" voted check");
		});
	});
	
});
