//弹窗 start
function msgBoxBg(msg){
    var bh = $("body").height(); 
    var bw = $("body").width();
    $(".MsgBox > p").html(msg);
    $(".MsgBoxBg").css({
        height:bh, 
        width:bw
    }).fadeIn(); 
    $(".MsgBox").fadeIn(); 
}
function closeBg() {
    $(".MsgBoxBg,.MsgBox").fadeOut(function(){
    	$(".MsgBox > p").html("");
    });
    
} 
$(function(){
	$("#MsgBoxBtn").click(function(){
		closeBg();
	});
});
//弹窗 end