$(function(){
	$(".form .username").blur(nameValidator);
	$(".form .password").blur(pwdValidator);
});	
function onfocus(obj){
	obj.focus(function(){
		$(this).removeClass('active');
		$(this).val('');
	})
}
function nameValidator(){
	var value=$(".form .username").val();   //获取username的值;
	var $username=$(".form .username");
	if(value==""||value==null){
		$username.val('账号不能为空!');
		$username.addClass('active');		
		return false;
	}else{
		onfocus($username);
	}
	return true;
}
function pwdValidator(){
	var value=$(".form .password").val();
	var $pwd=$('.form .password');
	if(value==""||value==null){
		$pwd.val('请输入密码!');
		$pwd.addClass('active');
		return false;
	}else{
		onfocus($pwd);
	}
	return true;
}	
function validateForm(){
	if(!nameValidator()||!pwdValidator()){
		return false;   // 表单不能提交
	}
	return true;
}	
	

	
