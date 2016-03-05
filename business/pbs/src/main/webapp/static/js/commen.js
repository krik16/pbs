$(function(){
	$("#changeBar a").each(function(i){
		$(this).click(function(){
			$("#changeBar a").removeClass("current1").removeClass("current2").removeClass("current3").removeClass("current4").eq(i).addClass("current"+(i+1));
			$("#changeBarDetail").show();
		    $("#changeBarDetail p").eq(i).show().siblings().hide();
		});
	});
});