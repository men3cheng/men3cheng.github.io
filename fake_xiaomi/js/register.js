$(function(){
	$(".form .username").blur(nameValidator);
	$(".form .passkey").blur(pkeyValidator);
	
	/*验证码*/
	function take(){
		var randomArray=[0,1,2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
		var result=[];
		function getRandom(array){
			var randomNum = array[Math.floor(Math.random()*array.length)];
			return randomNum;
		}
		for(var i=0;i<4;i++){
			result[result.length]=getRandom(randomArray);
		}
		return result;
	}
    var $able_key=$('.able-key');
    $able_key.html(take());
	$able_key.click(function(){	
		$(this).html(take());
	})
});	

function nameValidator(){
	var value=$(".form .username").val();   
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
function pkeyValidator(){
	var value=$(".form .passkey").val();
	var $pkey=$('.form .passkey');
	if(value==""||value==null){
		$pkey.val('请输入验证码!');
		$pkey.addClass('active');
		return false;
	}else{
		onfocus($pkey);
	}
	return true;
}	
function onfocus(obj){
	obj.focus(function(){
		$(this).removeClass('active');
		$(this).val('');
	})
}
function validateForm(){
	if(!nameValidator()||!pkeyValidator()){
		return false;   // 表单不能提交
	}
	return true;
}	



