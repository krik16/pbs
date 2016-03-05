$(function(){
   $("#market-info").mousedown(function(){
   	$("#market-info-wrap").show();
   });
   $(".function-bar").mousedown(function(){
   		$("#market-info-wrap").hide();
   });
   $(".toggle-change span").each(function(i){
      $(this).mousedown(function(){
      	$(this).addClass("current").siblings().removeClass("current");
      if(i==0){
        $("#dt-detail").hide();
        $("#md-detail").show();
      }else{
        $("#md-detail").hide();
        $("#dt-detail").show();
      }
      });

   });
});