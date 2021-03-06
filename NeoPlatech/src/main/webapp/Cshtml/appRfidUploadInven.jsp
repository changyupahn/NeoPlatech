<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String ip = request.getRemoteAddr();
String dt = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
System.out.println("["+ dt +"] " + ip + " - appRfidUploadInven.jsp");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cshtml</title>
</head>
<body>

<form method="post" action="/app/rfid/uploadInven.do">
<h2>재물조사 DATA 업로드</h2>

<div style="border:2px solid gray; padding:5px;">
재물조사 DATA 업로드
</div>

<h4>□ 요청URL : </h4>
http://boassoft.iptime.org:9777/app/rfid/uploadInven.do

<h4>□ 파라미터 : </h4>
<p>token<span style="color:red">(*)</span>: <input type="text" name="token" value="5YtJNAOtMJMt6giBcgxWwioPoC1gNCKi" size="70" /></p>
<p>deviceno<span style="color:red">(*)</span>: <input type="text" name="deviceno" value="326797f80960a0b1" /></p>
<p>invYear<span style="color:red">(*)</span>: <input type="text" name="invYear" value="2017" /> (재물조사년도)</p>
<p>invNo<span style="color:red">(*)</span>: <input type="text" name="invNo" value="1" /> (재물조사차수)</p>
<p>assetNo<span style="color:red">(*)</span>: <input type="text" name="assetNo" value="FA1-20000002" /> (물품번호)</p>
<p>tagReadDt<span style="color:red">(*)</span>: <input type="text" name="tagReadDt" value="20170125165420" /> (재물조사일시 - 년월일시분초)</p>
<p><input type="submit" value="요청결과 (Json)" /></p>
</form>

<br /><hr />

<br />전송 방식은 : http request (method=POST) 이며, json 포맷으로 리턴 받습니다.
<br /><span style="color:red">(*)</span> 는 필수 파라미터 입니다.

</body>
</html>