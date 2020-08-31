var mobile_mode;
var proxy_pass = "/api";

$(function() {
	mobile_mode = /^1[3456789]\d{9}$/; // 手机号模板
	var ua = navigator.userAgent.toLowerCase();
	var isWeixin = ua.indexOf('micromessenger') != -1;
	var isAndroid = ua.indexOf('android') != -1;
	var isIos = (ua.indexOf('iphone') != -1) || (ua.indexOf('ipad') != -1);
	console.log("isWeixin:" + isWeixin);
	console.log("isAndroid:" + isAndroid);
	console.log("isIos:" + isIos);
	$(window).unload(function(){
        //响应事件
        alert("获取到了页面要关闭的事件了！"); 
    }); 
})

/**
 * 键盘按键监控
 */
$(this).keydown(function(event) {
	var pageID = $("#pageID").val();
	if (pageID == "login") {
		// console.log(event.keyCode);
		if (event.keyCode == 13) {
			// layer.msg("回车键");
			// getLoginData();
			console.log("登录");
		}
	}
});

/**
 * 变量判空
 * @param obj
 * @returns {boolean}
 */
function isEmpty(obj) {
	if (obj == null || obj == "") {
		return true;
	} else {
		return false;
	}
}

/**
 * 获取URL中的参数
 * @param {Object} name
 */
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象  
	var r = window.location.search.substr(1).match(reg); //匹配目标参数
	if (r != null) return decodeURI(r[2]);
	return null;
}

function checkStr(str) {
	var regx = /['"#$%&\^*》>,."<《？，。！@#￥%……’”：/；]/;
	rs = regx.exec(str);
	if (rs != null) {
		return false;
	} else {
		return true;
	}
}
