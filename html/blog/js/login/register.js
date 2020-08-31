var layer, $;
var ip, ipName;
var sid;
layui.use('layer', function() {
	$("#username").focus();
	layer = layui.layer, $ = layui.jquery;
	// ip = returnCitySN["cip"]; // ip地址
	// ipName = returnCitySN["cname"]; // ip地址所在地
})

var vm = new Vue({
	el: '#register',
	data: {
		content: '获取验证码',
		totalTime: 60,
		canClick: true,
		username: "",
		phone: "",
		code: "",
		password: "",
		password_confirm: "",
		imageCode: "",
		imgCodeSrc: proxy_pass + "/account/generateImageCode?time=" + (new Date()).getTime()
	},
	methods: {
		countDown: function() {
			// 获取手机验证码
			getCode();
		},
		registerClick: function() {
			var flag = accountCheck(this.username, this.phone, this.code, this.password, this.password_confirm);
			if (flag) {
				// 注册
				accountRegister(this.username, this.phone, this.password, this.code);
			}
		},
		/**
		 * 更换一张图片验证码
		 */
		changeImageClick:function(){
			var imgSrc = $("#imgObj");
			var src = imgSrc.attr("src");  
			imgSrc.attr("src", proxy_pass + "/account/generateImageCode?time=" + (new Date()).getTime());
		}
	}
});


function getCode() {
	var phone = vm.$data.phone;
	var imageCode = vm.$data.imageCode;
	// 手机号不为空，格式正确，图片验证码不为空
	if (!isEmpty(phone) && mobile_mode.test(phone) && !isEmpty(imageCode)){
		var flag = sendSmsCode();
		if (flag) {
			if (!vm.$data.canClick) {
				return;
			}
			vm.$data.canClick = false;
			vm.$data.content = vm.$data.totalTime + 's后重新发送';
			var clock = setInterval(() => {
				vm.$data.totalTime--;
				vm.$data.content = vm.$data.totalTime + "s后重新发送";
				if (vm.$data.totalTime <= 0) {
					clearInterval(clock);
					vm.$data.content = "获取验证码";
					vm.$data.totalTime = 60;
					vm.$data.canClick = true;
				}
			}, 1000);
		}
		return;
	}
	var msg = "";
	if (isEmpty(phone)) msg = "请输入手机号";
	else if (!mobile_mode.test(phone)) msg = "请输入正确手机号";
	else if (isEmpty(imageCode)) msg = "请输入图片验证码";
	layer.msg(msg, {
		icon: 0
	});
}

/**
 * 获取验证码
 */
function sendSmsCode() {
	$.ajax({
		url: proxy_pass + "/sendMessage/sendSmsMessage",
		data: {
			phone: vm.$data.phone,
			imageCode: vm.$data.imageCode
		},
		type: "GET",
		dataType: "json",
		async: false,
		contentType: "application/josn;charset=UTF-8",
		success: function(data) {
			if (data.success == false) {
				layer.msg(data.message, {
					icon: 0
				});
				return false;
			}
			return true;
		}
	});
}

/**
 * 校验数据合规性
 * @param {Object} username
 * @param {Object} phone
 * @param {Object} code
 * @param {Object} password
 * @param {Object} password_confirm
 */
function accountCheck(username, phone, code, password, password_confirm) {
	var flag = false;
	if (isEmpty(username) || !checkStr(username)) {
		layer.msg("用户名不能为空，且不能带特殊字符", {
			icon: 0
		});
	} else if (isEmpty(phone)) {
		layer.msg("请输入手机号", {
			icon: 0
		});
	} else if (!mobile_mode.test(phone)) {
		layer.msg("请输入正确的手机号", {
			icon: 0
		});
	} else if (isEmpty(password)) {
		layer.msg("请输入密码", {
			icon: 0
		});
	} else if (password != password_confirm) {
		layer.msg("两次密码不一致", {
			icon: 0
		});
	} else {
		flag = true;
	}
	return flag;
}

function accountRegister(username, phone, password, code) {
	var param = {
		"username": username,
		"phone": phone,
		"password": password,
		"code": code
	};
	$.ajax({
		url: proxy_pass + "/account/RegisterCheck",
		data: JSON.stringify(param),
		type: "POST",
		dataType: "json",
		contentType: "application/json;charset=UTF-8",
		success: function(data) {
			if (data.success == false) {
				layer.msg(data.message, {
					icon: 0
				});
			} else {
				window.location.href = "login.html";
			}
		}

	});
}
