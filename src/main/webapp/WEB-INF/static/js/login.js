var app = new Vue({
    el: '#login',
    data: {
        user:{
            userName: null,
            password: null
        }
    },
    method:{
        checkName: function(){
            var name = this.user.userName;
            if(name == null || name == ""){
                //提示错误
                $('#count-msg').html("用户名不能为空");
                return false;
            }
            var reg = /^\w{3,10}$/;
            if(!reg.test(name)){
                $('#count-msg').html("输入3-10个字母或数字或下划线");
                return false;
            }
            $('#count-msg').empty();
            return true;
        },
        checkPassword: function () {
            var password = $this.user.password;
            if(password == null || password == ""){
                //提示错误
                $('#password-msg').html("密码不能为空");
                return false;
            }
            var reg = /^\w{3,10}$/;
            if(!reg.test(password)){
                $('#password-msg').html("输入3-10个字母或数字或下划线");
                return false;
            }
            $('#password-msg').empty();
            return true;
        },
        login: function (e) {
            console("login: " + this.userName + ", password: " + this.password);
            e.preventDefault();
        }
    }
});


// $(function(){
// 	$('#username').focus().blur(checkName);
// 	$('#password').blur(checkPassword);
// });
//
// function checkName(){
// 	var name = $('#username').val();
// 	if(name == null || name == ""){
// 		//提示错误
// 		$('#count-msg').html("用户名不能为空");
// 		return false;
// 	}
// 	var reg = /^\w{3,10}$/;
// 	if(!reg.test(name)){
// 		$('#count-msg').html("输入3-10个字母或数字或下划线");
// 		return false;
// 	}
// 	$('#count-msg').empty();
// 	return true;
// }
//
// function checkPassword(){
// 	var password = $('#password').val();
// 	if(password == null || password == ""){
// 		//提示错误
// 		$('#password-msg').html("密码不能为空");
// 		return false;
// 	}
// 	var reg = /^\w{3,10}$/;
// 	if(!reg.test(password)){
// 		$('#password-msg').html("输入3-10个字母或数字或下划线");
// 		return false;
// 	}
// 	$('#password-msg').empty();
// 	return true;
// }