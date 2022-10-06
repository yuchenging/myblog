	// input
	$(function(){
		//鼠标焦点
		$(":input.user").focus(function(){
			$(this).addClass("userhover");                          
		}).blur(function(){
			$(this).removeClass("userhover")
		});
		$(":input.pwd").focus(function(){
			$(this).addClass("pwdhover");                          
		}).blur(function(){
			$(this).removeClass("pwdhover")
		});
		
		//输入用户名
		$(".user").focus(function(){
			var username = $(this).val();
			if(username == ''){
			   $(this).val('');
			}
		 });
		 $(".user").blur(function(){
			var username = $(this).val();
			if(username == ''){
			   $(this).val('');
			}
		 });
		 
		 //输入密码
		 $(".pwd").focus(function(){
			var password = $(this).val();
			if(password == ''){
			   $(this).val('');
			}
		 });
		 $(".pwd").blur(function(){
			var password = $(this).val();
			if(password == ''){
			   $(this).val('');
			}
		 });
	
	});
	
	//用户登录表单提交
	function checkLogin() {

		//获取用户名和密码
		var userName=$("#userName").val();
		var userPwd=$("#userPwd").val();
		//判断用户名和密码是否为空
		if(isEmpty(userName))
		{
			alert("用户名不能为空！");
			//$("#msg").html("用户名不能为空！");
			return;
		}
		if(isEmpty(userPwd))
		{
			alert("密码不能为空！");
			//$("#msg").html("密码不能为空！");
			return;
		}
		//提交表单
		$("#loginForm").submit();
	}
	