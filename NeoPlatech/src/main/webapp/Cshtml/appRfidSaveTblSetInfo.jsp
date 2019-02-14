<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String ip = request.getRemoteAddr();
String dt = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
System.out.println("["+ dt +"] " + ip + " - appRfidSaveTblSetInfo.jsp");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cshtml</title>
</head>
<body>

<form method="post" action="/app/rfid/saveTblSetInfo.do">
<h2>초기세팅정보 수정</h2>

<div style="border:2px solid gray; padding:5px;">
초기세팅정보 수정
</div>

<h4>□ 요청URL : </h4>
http://boassoft.iptime.org:9777/app/rfid/saveTblSetInfo.do

<h4>□ 파라미터 : </h4>
<p>token<span style="color:red">(*)</span>: <input type="text" name="token" value="5YtJNAOtMJMt6giBcgxWwioPoC1gNCKi" size="70" /></p>
<p>deviceno<span style="color:red">(*)</span>: <input type="text" name="deviceno" value="326797f80960a0b1" /></p>
<p>
	aamSeq<span style="color:red">(*)</span>: <input type="text" name="aamSeq" value="3" />
	항목관리번호
</p>
<p>
	columnType<span style="color:red">(*)</span>: <input type="text" name="columnType" value="1" />
	1필수항목(체크일 경우), 2선택항목(체크 해제일 경우)
</p>
<p><input type="submit" value="요청결과 (Json)" /></p>
</form>

<br /><hr />

<br />전송 방식은 : http request (method=POST) 이며, json 포맷으로 리턴 받습니다.
<br /><span style="color:red">(*)</span> 는 필수 파라미터 입니다.

</body>
</html>