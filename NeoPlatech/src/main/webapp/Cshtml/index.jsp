<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String ip = request.getRemoteAddr();
String dt = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
System.out.println("["+ dt +"] " + ip + " - index.jsp");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<p><a href="/Cshtml/appRfidLogin.jsp">1. /app/rfid/login.do [인증요청 - 로그인]</a></p>
<p><a href="/Cshtml/appRfidAuth.jsp">2. /app/rfid/auth.do [인증요청 - 토큰]</a></p>
<p><a href="/Cshtml/appRfidInitTblSetInfo.jsp">3. /app/rfid/initTblSetInfo.do [초기세팅정보 조회]</a></p>
<p><a href="/Cshtml/appRfidSaveTblSetInfo.jsp">4. /app/rfid/saveTblSetInfo.do [초기세팅정보 수정]</a></p>
<p><a href="/Cshtml/appRfidSelectInventoryList.jsp">5. /app/rfid/selectInventoryList.do [재물조사 목록 (내려받기용)]</a></p>
<p><strike><a href="/Cshtml/appRfidAssetImage.jsp">6. /app/rfid/getImage.do [재물조사 사진 URL]</a> (사용안함)</strike></p>
<p><a href="/Cshtml/appRfidUploadImage.jsp">7. /app/rfid/uploadImage.do [재물조사 사진 업로드]</a></p>
<p><a href="/Cshtml/appRfidDeleteImage.jsp">8. /app/rfid/deleteImage.do [재물조사 사진 삭제]</a></p>
<p><a href="/Cshtml/appRfidUploadInven.jsp">9. /app/rfid/uploadInven.do [재물조사 DATA 업로드]</a></p>
<p><a href="/Cshtml/appRfidImageList.jsp">10. /app/rfid/imageList.do [물품 이미지 목록]</a></p>

</body>
</html>