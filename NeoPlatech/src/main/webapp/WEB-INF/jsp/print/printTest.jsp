<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<object classid="clsid:42575D0B-C9FC-4CA7-BABC-749803F00D90" CODEBASE="/TagPrintX.ocx#Version=4,73,8259,0" width="0" height="0" id="TagPrintX"></object>

<%
	request.setCharacterEncoding("euc-kr");
	String format = request.getParameter("format");
	String ip = request.getParameter("ip");
	String port = request.getParameter("port");
	String assetNo = request.getParameter("assetNo");
	String rfidNo = "2CB4832D4A" + assetNo;
	String assetName = request.getParameter("assetName");
	String spec = request.getParameter("spec");
	String user = request.getParameter("user");
	
	format = "D:\\\\project\\\\boasAssets\\\\workspace\\\\init\\\\WebContent\\\\print\\\\labeln.txt";
	ip = "150.183.138.209";
	port = "9000";
	assetNo = "00110096-00";
	rfidNo = "2CB4832D4A" + assetNo;
	assetName = "책상";
	spec = "책상(1400)";
	user = "";
%>

<script language="javascript">
	TagPrintX.setPrintIP('<%=ip%>');
	TagPrintX.setPrintPort('<%=port%>');
	TagPrintX.setFileName('<%=format%>');
	TagPrintX.setTagDts('<%=assetNo%>' + "!!" + '<%=rfidNo%>' + "!!" + '<%=assetName%>' + "!!" + '<%=spec%>' + "!!" + '<%=user%>');
	TagPrintX.TagPrint();
</script>
	
<script language="javascript" for="TagPrintX" event="PrintComplete(str)">
	var arrResult = str.split(',');
	if (arrResult[1] == '0') {
		alert('성공');
		history.go(-1);
	} else {
		alert('실패');
		history.go(-1);
	}
</script>
		
</body>
</html>