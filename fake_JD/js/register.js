window.onload = function() {
	// 1. 用户名
	$("#username").focus(function() {
		elemFocus("username_msg", "4-20位字符，支持英文、数字及'-'、'_'组合");
	}).blur(userValidator);
	// 2. 密码
	$("#password").focus(function() {
		elemFocus("pwd_msg", "6-20位字符，可使用字母、数字的组合");
	}).blur(pwdValidator);
	// 3. 确认密码
	$("#pwdRepeat").focus(function() {
		elemFocus("pwdRepeat_msg", "6-20位字符，可使用字母、数字的组合");
	}).blur(pwdRepeatValidator);
	// 4. Email
	$("#mail").focus(function() {
		elemFocus("mail_msg", "完成验证后，可以使用该邮箱登录和找回密码");
	}).blur(emailValidator);
}

// 定义函数 - 通用的信息提示
function elemFocus(eleId, text) {
	var ele_msg = $("#" + eleId);
	ele_msg.text(text);
	ele_msg.attr("class", "focus");
}
// 定义验证用户名的函数
function userValidator() {	
	var value = $("#username").val();  // 获取用户名输入的值	
	var username_msg = $("#username_msg");  // 获取用于显示提示信息的元素	
	if (value == "" || value == null) {
		username_msg.text("用户名不能为空");
		username_msg.attr("class", "error");
		return false;
	} else if (value.length < 4 || value.length > 20) {
		username_msg.text("用户名的长度不正确");
		username_msg.attr("class", "error");
		return false;
	} else if (!/^[a-zA-Z0-9-_]{4,20}$/.test(value)) {
		username_msg.text("用户名输入不正确");
		username_msg.attr("class", "error");
		return false;
	}
	// 验证通过修改正确样式
	if (!username_msg.hasClass("hide")) {
		username_msg.text("");
		username_msg.attr("class", "hide");
	}
	return true;
}
// 定义验证密码的函数
function pwdValidator() {
	var value = $("#password").val();
	var pwd_msg = $("#pwd_msg");
	if (value == "" || value == null) {
		pwd_msg.text("密码不能为空");
		pwd_msg.attr("class", "error");
		return false;
	} else if (value.length < 6 || value.length > 20) {
		pwd_msg.text("密码的长度不正确");
		pwd_msg.attr("class", "error");
		return false;
	} else if (!/^[a-zA-Z0-9]{6,20}$/.test(value)) {
		pwd_msg.text("密码输入不正确");
		pwd_msg.attr("class", "error");
		return false;
	}
	if (!pwd_msg.hasClass("hide")) {
		pwd_msg.text("");
		pwd_msg.attr("class", "hide");
	}
	return true;
}
// 定义确认密码验证的函数
function pwdRepeatValidator() {
	var value = $("#pwdRepeat").val();
	var pwdRepeat_msg = $("#pwdRepeat_msg");
	var pwd = $("#password").val();
	if (value == "" || value == null) {
		pwdRepeat_msg.text("密码不能为空");
		pwdRepeat_msg.attr("class", "error");
		return false;
	} else if (value.length < 6 || value.length > 20) {
		pwdRepeat_msg.text("密码的长度不正确");
		pwdRepeat_msg.attr("class", "error");
		return false;
	} else if (!/^[a-zA-Z0-9]{6,20}$/.test(value)) {
		pwdRepeat_msg.text("密码输入不正确");
		pwdRepeat_msg.attr("class", "error");
		return false;
	} else if (value != pwd) {
		pwdRepeat_msg.text("两次密码输入不一致");
		pwdRepeat_msg.attr("class", "error");
		return false;
	}
	if (!pwdRepeat_msg.hasClass("hide")) {
		pwdRepeat_msg.text("");
		pwdRepeat_msg.attr("class", "hide");
	}
	return true;
}
// 定义Email验证的函数
function emailValidator() {
	var value = $("#mail").val();
	var email_msg = $("#mail_msg");
	if (value == "" || value == null) {
		email_msg.text("Email不能为空");
		email_msg.attr("class", "error");
		return false;
	} else if (!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value)) {
		email_msg.text("Email格式不正确");
		email_msg.attr("class", "error");
		return false;
	}
	if (!email_msg.hasClass("hide")) {
		email_msg.text("");
		email_msg.attr("class", "hide");
	}
	return true;
}
function validateForm() {
	if (!userValidator() || !pwdValidator() || !pwdRepeatValidator() || !emailValidator()) {
		return false;
	}
	return true;
}