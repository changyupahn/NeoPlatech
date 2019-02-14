<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String ip = request.getRemoteAddr();
String dt = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
System.out.println("["+ dt +"] " + ip + " - appRfidUploadImage.jsp");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cshtml</title>
</head>
<body>

<form method="post" action="/asset/image/upload.do" enctype="multipart/form-data">
<h2>재물조사 사진 업로드</h2>

<div style="border:2px solid gray; padding:5px;">
재물조사 사진 업로드
</div>

<h4>□ 요청URL : </h4>
http://boassoft.iptime.org:10061/asset/image/upload.do (multipart)

<h4>□ 파라미터 : </h4>
<p>deviceno<span style="color:red">(*)</span>: <input type="text" name="deviceno" value="5e748d3589089d47" /></p>
<p>asset_no<span style="color:red">(*)</span>: <input type="text" name="assetNo" value="1-20180628-00001" /> (자산번호)</p>
<p>file_dt<span style="color:red">(*)</span>: <input type="text" name="file_dt" value="20180719135000" /> (이미지생성일시- 년월일시분초)</p>
<p>
	file<span style="color:red">(*)</span>: <input type="file" name="file" />
	물품 이미지 파일
</p>
<p><input type="submit" value="요청결과 (Json)" /></p>
</form>

<br /><hr />

<br />전송 방식은 : http request (method=POST, enctype=multipart/form-data) 이며, json 포맷으로 리턴 받습니다.
<br /><span style="color:red">(*)</span> 는 필수 파라미터 입니다.

</body>
</html>