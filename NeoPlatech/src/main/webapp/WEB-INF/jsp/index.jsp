<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>

<style type="text/css">
body,div,ul,li{margin:0;padding:0;}
#login{width:854px;height:337px;margin:0 auto;border:solid 1px #cdcdcd;text-align:center;}
#login h1{margin:20px;padding:0 0 11px 20px;border-bottom:solid 3px #284d82;text-align:left;}
#login h2{width:100%;margin-bottom:42px;padding:0 0 11px 20px;border-bottom:dashed 1px #d7d7d7;text-align:left;}
.img{margin:0 20px 0 20px;float:left;}
.login_form{float:left;display:block;width:395px;padding-bottom:40px;border-bottom:dashed 1px #d7d7d7;}
.login_txt{width:186px;height:22px;border:solid 1px #999999;}
</style>

<script type="text/javascript">
function fnValidationChk(){

	if( $('#user_id').val() == "" ){
		alert("아이디를 입력해주세요");
		return false;
	}
	if( $('#user_pw').val() == "" ){
		alert("비밀번호를 입력해주세요");
		return false;
	}

	return true;
}
$(document).ready(function(){
	<%--
	$('#user_id').val("boassoft");
	$('#user_pw').val("boassoft");
	--%>
});
</script>

</head>
<body style="margin-top:100px;">

	<div id="login">
		<form name="loginForm" method="post" action="/kp1000/kp1010IdLoginProc.do" onsubmit="return fnValidationChk()" >

		<h1 style="color:#000000;font-size:42px;padding-top:20px;font-family:'Arial Bold';font-weight:normal;text-shadow: 2px 2px 2px #aaaaaa;">
			<img src="/images/logo/neo_logo1_533_80.png" width="300" alt="네오플라텍" />
		</h1>

		<div class="img">
	    	<img src="/images/login/login_img.png" alt="" width="300"/>
	    </div>
	    <div class="login_form">
	    	<h2><img src="/images/login/login_h2.gif" alt="login" /></h2>
	        	<table cellpadding="0" cellspacing="0" border="0" align="center">
	        	<tbody>
	            <tr>
	            	<td height="30"><img src="/images/login/login_id.gif" alt="아이디" /></td>
	                <td rowspan="2" width="8"></td>
	                <td>
	                	<input type="text" name="user_id" id="user_id" class="login_txt" tabindex="1" />
	                </td>
	                <td rowspan="2" width="8"></td>
	                <td rowspan="2"><input type="image" src="/images/login/login_btn.gif" alt="로그인" style="cursor:pointer;width:65px;height:56px;" tabindex="3" /></td>
	            </tr>
	            <tr>
	            	<td height="30"><img src="/images/login/login_pw.gif" alt="비밀번호" /></td>
	                <td><input type="password" name="user_pw"  id="user_pw" class="login_txt" tabindex="2" /></td>
	            </tr>
	            </tbody>
	        </table>
	    </div>
		</form>
	</div>

	<iframe src="/validationQuery.do" width="0" height="0"></iframe>

</body>
</html>



