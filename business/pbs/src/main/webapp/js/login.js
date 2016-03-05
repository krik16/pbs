//回车登录事件
$.ajaxSetup({
	cache : false
});
function login() {
	var theEvent = window.event || arguments.callee.caller.arguments[0];
	var key = theEvent ? theEvent.keyCode : theEvent.which;
	if (key == "13") {

		var type = $('#isAdmin').val();
		if (type == "0") {
			
			 var rsa = new RSAKey();
			  rsa.setPublic("9ed4dfad478b9d6eeade1d2c43565442bfb48bc3b2cb162c722e85c48d514d364c699e5b9a9a6cde465676dcf535e832cf229088f41b581cc87e9fa025e2995ce272847c9d0e8282ebe0cfd5ce50917805a5efe0c517129957b300ef2afb82c5ff78b43258069c85cd7b80c98b3057d70510fff0f641da27f65e735f72c1cbe7","10001");
//			$('#username').val(hex2b64(rsa.encrypt($('#username').val())));
			$('#password').val(hex2b64(rsa.encrypt($('#password').val())));
		} else {
			$('#password').val($.md5($('#password').val() + salt));
		}
		document.f.submit();
		return false;
	}
};


function clickLogin() {

	var type = $('#isAdmin').val();
	if (type == "0") {
		
		 var rsa = new RSAKey();
		  rsa.setPublic("9ed4dfad478b9d6eeade1d2c43565442bfb48bc3b2cb162c722e85c48d514d364c699e5b9a9a6cde465676dcf535e832cf229088f41b581cc87e9fa025e2995ce272847c9d0e8282ebe0cfd5ce50917805a5efe0c517129957b300ef2afb82c5ff78b43258069c85cd7b80c98b3057d70510fff0f641da27f65e735f72c1cbe7","10001");
//		$('#username').val(hex2b64(rsa.encrypt($('#username').val())));
		$('#password').val(hex2b64(rsa.encrypt($('#password').val())));
	} else {
		$('#password').val($.md5($('#password').val() + salt));
	}
	document.f.submit();
	return false;
	//	}
	
};