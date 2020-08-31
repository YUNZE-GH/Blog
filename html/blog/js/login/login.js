var layer, $;
var ip, ipName;
var sid;
layui.use('layer', function() {
	$("#phone").focus();
	layer = layui.layer, $ = layui.jquery;
	// ip = returnCitySN["cip"]; // ip地址
	// ipName = returnCitySN["cname"]; // ip地址所在地
})

var data = {
	phone: null,
	password: null
};

var vm = new Vue({
	el: "#sign",
	data: data,
	methods: {
		loginClick: function() {
			var phone = vm.$data.phone;
			var password = vm.$data.password;
			var flag = loginCheck(phone, password);
			console.log("check:" + flag);
			if (flag) {
				window.location.href = "index.html?sid=" + sid;
			}
		},
		antherLoginClick: function() {
			layer.msg("此功能暂未开放", {
				icon: 0
			});
		}
	}
});

/**
 * 登录校验
 * @param {Object} phone	手机号
 * @param {Object} password	密码
 */
function loginCheck(phone, password) {
	var flag = false;
	if (isEmpty(phone)) {
		layer.msg("请输入手机号", {
			icon: 0
		});
	} else if (!mobile_mode.test(phone)) {
		layer.msg("请输入正确手机号", {
			icon: 0
		});
	} else if (isEmpty(password)) {
		layer.msg("请输入密码", {
			icon: 0
		});
	} else {
		$.ajax({
			url: proxy_pass + "/account/loginCheck/" + phone + "/" + password,
			type: "GET",
			dataType: "json",
			contentType: "application/json;charset=UTF-8",
			async:false,
			success: function(data) {
				console.log(data);
				if(data.success == true) {
					sid = data.sid;
					flag = true;
				}
			}
		});
	}
	return flag;
}
