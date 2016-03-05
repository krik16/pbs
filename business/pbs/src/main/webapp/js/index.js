$(function() {
    //滚动条优化
    $("html").niceScroll({  
        cursorcolor:"#5b5b5b",  
        cursoropacitymax:1,  
        touchbehavior:false,  
        cursorwidth:"8px",  
        cursorborder:"0",  
        cursorborderradius:"5px"  
    });

    $("#tree").niceScroll({  
        cursorcolor:"#5b5b5b",  
        cursoropacitymax:1,  
        touchbehavior:false,  
        cursorwidth:"8px",  
        cursorborder:"0",  
        cursorborderradius:"5px"  
    }); 
	
//	$(".brandBox-list").niceScroll({  
//        cursorcolor:"#5b5b5b",  
//        cursoropacitymax:1,  
//        touchbehavior:false,  
//        cursorwidth:"8px",  
//        cursorborder:"0",  
//        cursorborderradius:"5px"  
//    }); 

//    $(".brandLinkList").niceScroll({  
//        cursorcolor:"#5b5b5b",  
//        cursoropacitymax:1,  
//        touchbehavior:false,  
//        cursorwidth:"8px",  
//        cursorborder:"0",  
//        cursorborderradius:"5px"  
//    }); 

    // $(".inputSelect").selectmenu();   //下拉类表控件
//    $(".inputSelectMax").selectmenu();   //下拉类表控件
    $(".inputSelectMax").parent().find("span.ui-selectmenu-text").css("width","140px");

    //单选按钮样式调整
	$('.shopsInfo label').click(function(){
		var radioId = $(this).attr('name');
		$('label').removeAttr('class') && $(this).attr('class', 'checked');
		$('input[type="radio"]').removeAttr('checked') && $('#' + radioId).attr('checked', 'checked');
	});

    //复选按钮
    $( ".CheckBox" ).button(); 

    // $( ".datepicker" ).datepicker();    //日期控件
    // $( ".time" ).datepicker();    //日期控件
    // $( ".date" ).datepicker();    //日期控件

    // $('#DateTime').datetimepicker();    //日期加时间
    // $('#DateTime').timepicker();   //时间
    // $('#Date').datepicker();   //日期
    // $('#Date2').datepicker();   //日期
    
    $('#DateTime').click(function(){
        $('#DateTime').addClass("checked");
    });
    $('#DateTime2').click(function(){
        $('#DateTime2').addClass("checked");
    });
    $('#DateTime').datetimepicker({   //汉化版日期
        timeFormat: "HH:mm:ss",
        dateFormat: "yy-mm-dd",
        onSelect: function(dateText, inst) {
            $('#DateTime2').datepicker('option', 'minDate',new Date(dateText.replace('-',',')));             
        } 
    });
    $('#DateTime2').datetimepicker({   //汉化版日期
        timeFormat: "HH:mm:ss",
        dateFormat: "yy-mm-dd",
        onSelect: function(dateText, inst) {
            $('#DateTime').datepicker('option', 'maxDate',new Date(dateText.replace('-',',')));             
        } 
    });
    $('#Time').timepicker();   //时间

    //汉化日期+判断开始时间是否小于结束时间
    $('#Date').click(function(){
        $('#Date').addClass("checked");
    });
    $('#Date2').click(function(){
        $('#Date2').addClass("checked");
    });
    $('#Date').datepicker({            
        dateFormat: "yy-mm-dd",
        onSelect: function(dateText, inst) {
            $('#Date2').datepicker('option', 'minDate',new Date(dateText.replace('-',',')));             
        } 
    });
    $('#Date2').datepicker({
        dateFormat: "yy-mm-dd",
        onSelect: function(dateText, inst) {
            $('#Date').datepicker('option', 'maxDate',new Date(dateText.replace('-',',')));  
        }    
    });
   

});








