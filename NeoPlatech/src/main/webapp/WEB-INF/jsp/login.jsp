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
	if( $('#GEUBYEO_CD').val() == "" ){
		alert("아이디를 입력해주세요");
		return false;
	}
	if( $('#PWD_NO').val() == "" ){
		alert("비밀번호를 입력해주세요");
		return false;
	}

	return true;
}
</script>

</head>
<body style="margin-top:100px;">

	<div id="login">
		<form name="loginForm" method="post" action="/kp1000/kp1010.do" onsubmit="return fnValidationChk()" >

		<h1><img src="/images/login/login_h1.gif" alt="한국항공우주연구원 물품관리시스템" /></h1>
		<div class="img">
	    	<img src="/images/login/login_img.gif" alt="" />
	    </div>
	    <div class="login_form">
	    	<h2><img src="/images/login/login_h2.gif" alt="login" /></h2>
	        	<table cellpadding="0" cellspacing="0" border="0" align="center">
	        	<tbody>
	            <tr>
	            	<td height="30"><img src="/images/login/login_id.gif" alt="아이디" /></td>
	                <td rowspan="2" width="8"></td>
	                <td>
	                	<input type="text" class="login_txt" name="GEUBYEO_CD" id="GEUBYEO_CD" tabindex="1" />
	                </td>
	                <td rowspan="2" width="8"></td>
	                <td rowspan="2"><input type="image" src="/images/login/login_btn.gif" alt="로그인" style="cursor:pointer;width:65px;height:56px;" tabindex="3" /></td>
	            </tr>
	            <tr>
	            	<td height="30"><img src="/images/login/login_pw.gif" alt="비밀번호" /></td>
	                <td><input name="PWD_NO" type="password" id="PWD_NO" class="login_txt" tabindex="2" /></td>
	            </tr>
	            </tbody>
	        </table>
	    </div>
		</form>
	</div>

</body>
</html>



