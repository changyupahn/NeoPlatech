<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String ip = request.getRemoteAddr();
String dt = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
System.out.println("["+ dt +"] " + ip + " - appRfidInitTblSetInfo.jsp");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cshtml</title>
</head>
<body>

<form method="post" action="/app/rfid/initTblSetInfo.do">
<h2>초기세팅정보 조회</h2>

<div style="border:2px solid gray; padding:5px;">
고정항목 : assetNo, rfidNo, tagReadDt
<br />* 고정항목이란 어떠한 업체로 내려받기를 하더라도 항상 포함되는 항목이며, 항목의 physicalName (물리적 영문 이름) 이 변하지 않습니다.
<br />
<br />astcolMngList 항목값 중 columnType=1 필수 항목이며, columnType=2 인 것은 선택 항목 입니다.
<br />
<br />astcolMngList 항목값 중 cmcodeYn=Y 인 것은 분류 값으로 관리 되며, Y 일 경우 cmcodeList 에 분류 목록이 포함됨.
<br />
<br />astcolMngList 항목값 중 dataType=VARCHAR 또는 DECIMAL
<br />
<br />astcolMngList 항목값 중 dataSize=데이터 사이즈
<br />
<br />astcolMngList 항목값 중 dataDispType=1기본형, 2문자형, 3숫자형, 4날짜형
<br />* 숫자형만 dataType=DECIMAL 이며, 그 외의 타입은 모두 VARCHAR
<br />
<br />astcolMngList 항목값 중 nullYn=Y널허용하지않음, N널허용
</div>

<h4>□ 요청URL : </h4>
http://boassoft.iptime.org:9777/app/rfid/initTblSetInfo.do

<h4>□ 파라미터 : </h4>
<p>token<span style="color:red">(*)</span>: <input type="text" name="token" value="5YtJNAOtMJMt6giBcgxWwioPoC1gNCKi" size="70" /></p>
<p>deviceno<span style="color:red">(*)</span>: <input type="text" name="deviceno" value="326797f80960a0b1" /></p>
<p><input type="submit" value="요청결과 (Json)" /></p>
</form>

<br /><hr />

<br />전송 방식은 : http request (method=POST) 이며, json 포맷으로 리턴 받습니다.
<br /><span style="color:red">(*)</span> 는 필수 파라미터 입니다.

</body>
</html>