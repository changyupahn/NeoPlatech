<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String ip = request.getRemoteAddr();
String dt = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
System.out.println("["+ dt +"] " + ip + " - appRfidAssetImage.jsp");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cshtml</title>

<script>
function fnGetImage() {
	var frm = document.sForm;
	var token = frm.token.value;
	var deviceno = frm.deviceno.value;
	var assetNo = frm.assetNo.value;
	var fileDt = frm.fileDt.value;
	var tn = frm.tn.value;
	
	var imageUrl = "http://boassoft.iptime.org:9777/app/rfid/getImage.do";
	imageUrl = imageUrl + "?token=" + token + "&deviceno=" + deviceno + "&assetNo=" + assetNo + "&fileDt=" + fileDt + "&tn=" + tn;
	
	document.getElementById("assetImage").src = imageUrl;
}
</script>

</head>
<body>

<form id="sForm" name="sForm" method="post" action="/app/rfid/getImage.do">
<h2>재물조사 사진 URL</h2>

<div style="border:2px solid gray; padding:5px;">
재물조사 사진 URL
</div>

<h4>□ 요청URL : </h4>
http://boassoft.iptime.org:9777/app/rfid/getImage.do

<h4>□ 파라미터 : </h4>
<p>token<span style="color:red">(*)</span>: <input type="text" name="token" value="5YtJNAOtMJMt6giBcgxWwioPoC1gNCKi" size="70" /></p>
<p>deviceno<span style="color:red">(*)</span>: <input type="text" name="deviceno" value="326797f80960a0b1" /></p>
<p>assetNo<span style="color:red">(*)</span>: <input type="text" name="assetNo" value="FA1-20000002" /> (물품번호)</p>
<p>fileDt<span style="color:red">(*)</span>: <input type="text" name="fileDt" value="20140924100538" /> (이미지생성일시- 년월일시분초)</p>
<p>
	tn<span style="color:red">(*)</span>: <input type="text" name="tn" value="Y" />
	Y썸네일 이미지, N원본 이미지
</p>
<p><input type="button" value="요청결과 (Image)" onclick="fnGetImage()" /></p>
</form>

<p><img src="" id="assetImage" alt="" /></p>

<br /><hr />

<br />전송 방식은 : http request (method=GET) 입니다.
<br /><span style="color:red">(*)</span> 는 필수 파라미터 입니다.

</body>
</html>