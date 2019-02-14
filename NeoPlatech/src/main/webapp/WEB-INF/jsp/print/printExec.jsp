<%@page import="egovframework.com.cmm.service.EgovProperties"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList inventoryDetailList = RequestUtil.getCommonList(request, "inventoryDetailList");
CommonList assetList = RequestUtil.getCommonList(request, "assetList");
CommonMap printInfo = RequestUtil.getCommonMap(request, "printInfo");
String printType = cmRequest.getString("printType");
String tmpTitle = cmRequest.getString("tmpTitle").trim();

String printLabelPathN = EgovProperties.getProperty("Globals.PrintLabel.PathN");
String printLabelPathS = EgovProperties.getProperty("Globals.PrintLabel.PathS");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body onload="fnPrintExec()">
	<object classid="clsid:42575D0B-C9FC-4CA7-BABC-749803F00D90" CODEBASE="/TagPrintX.ocx#Version=4,73,8259,0" width="0" height="0" id="TagPrintX"></object>

<%
	String format = printLabelPathN;
	String ip = "150.183.138.209";
	String port = "9000";
	String printFnName = "tagPrint";
	
	if ("S".equals(printType)) {
		format = printLabelPathS;
		ip = "150.183.138.208";
		port = "9000";
		printFnName = "tagPrintS";
	}	
	
	/*
	String assetNo = "00110096-00";
	String rfidNo = "2CB4832D4A" + assetNo;
	String assetName = "책상";
	String spec = "책상(1400)";
	String user = "";
	*/
%>

<script language="javascript">
	function tagPrint(ip, port, format, assetNo, rfidNo, assetName, spec, user, usePositionName){
		TagPrintX.setPrintIP(ip);
		TagPrintX.setPrintPort(port);
		TagPrintX.setFileName(format);
		TagPrintX.setTagDts(assetNo + "!!" + rfidNo + "!!" + assetName + "!!" + spec + "!!" + user + "!!" + usePositionName);
		TagPrintX.TagPrint();
	}
	function tagPrintS(ip, port, format, assetNo, rfidNo, assetName, spec, user, usePositionName){
		TagPrintX.setPrintIP(ip);
		TagPrintX.setPrintPort(port);
		TagPrintX.setFileName(format);
		TagPrintX.setTagDts(assetNo + "!!" + rfidNo + "!!" + assetName + "!!" + user + "!!" + usePositionName);
		TagPrintX.TagPrint();
	}
</script>

<script language="javascript" for="TagPrintX" event="PrintComplete(str)">
	var arrResult = str.split(',');
	if (arrResult[1] == '0') {
		//alert('정상 출력 되었습니다.');
		//history.go(-1);
	} else {		
		alert('태그 출력 실패');
		//history.go(-1);
	}
</script>

<script type="text/javascript">
function fnPrintExec(){
	<%
	for (int i=0; i<inventoryDetailList.size(); i++) {
		CommonMap inventoryDetail = inventoryDetailList.getMap(i);
		String fixassetCode = inventoryDetail.getString("asset_no");
		String rfidNo = "2CB4832D4A" + fixassetCode;
		String fixassetName = inventoryDetail.getString("asset_name");
		String fixassetSize = inventoryDetail.getString("asset_size");
		String empName = StringUtil.nvl(inventoryDetail.getString("cng_user_name"), inventoryDetail.getString("user_name"));
		String usePositionName = inventoryDetail.getString("dept_name");
		
		//물품명 Max Length 지정
		fixassetName = StringUtil.strCut(fixassetName, 30, true);
		fixassetSize = StringUtil.strCut(fixassetSize, 30, true);
		
		//fixassetName = StringUtil.strCut("가나다라마바사아자차카타파하가나다라마바사아자차카타파하", 30, true);
		//fixassetName = StringUtil.strCut("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz", 30, true);
		
		if (!"".equals(tmpTitle)) {
			usePositionName = tmpTitle;
		}
		
	%>
	<%=printFnName%>("<%=ip%>", "<%=port%>", "<%=format%>", "<%=fixassetCode%>", "<%=rfidNo%>", "<%=fixassetName%>", "<%=fixassetSize%>", "<%=empName%>", "<%=usePositionName%>");
	<%
	}
	%>
	
	<%
	for (int i=0; i<assetList.size(); i++) {
		CommonMap asset = assetList.getMap(i);
		String fixassetCode = asset.getString("asset_no");
		String rfidNo = "2CB4832D4A" + fixassetCode;
		String fixassetName = asset.getString("asset_name");
		String fixassetSize = asset.getString("asset_size");
		String empName = asset.getString("user_name");
		String usePositionName = asset.getString("dept_name");
		
		//물품명 Max Length 지정
		fixassetName = StringUtil.strCut(fixassetName, 30, true);
		fixassetSize = StringUtil.strCut(fixassetSize, 30, true);
		
		//fixassetName = StringUtil.strCut("가나다라마바사아자차카타파하가나다라마바사아자차카타파하", 30, true);
		//fixassetName = StringUtil.strCut("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz", 30, true);
		
		if (!"".equals(tmpTitle)) {
			usePositionName = tmpTitle;
		}
		
	%>
	<%=printFnName%>("<%=ip%>", "<%=port%>", "<%=format%>", "<%=fixassetCode%>", "<%=rfidNo%>", "<%=fixassetName%>", "<%=fixassetSize%>", "<%=empName%>", "<%=usePositionName%>");
	<%
	}
	%>
	
	<%
	if (printInfo != null && !printInfo.isEmpty()) {
		String fixassetCode = printInfo.getString("asset_no");
		String rfidNo = "2CB4832D4A" + fixassetCode;
		String fixassetName = printInfo.getString("asset_name");
		String fixassetSize = printInfo.getString("asset_size");
		String empName = printInfo.getString("user_name");
		String usePositionName = printInfo.getString("dept_name");
		
		//물품명 Max Length 지정
		fixassetName = StringUtil.strCut(fixassetName, 30, true);
		fixassetSize = StringUtil.strCut(fixassetSize, 30, true);
		
		//fixassetName = StringUtil.strCut("가나다라마바사아자차카타파하가나다라마바사아자차카타파하", 30, true);
		//fixassetName = StringUtil.strCut("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz", 30, true);
		
		if (!"".equals(tmpTitle)) {
			usePositionName = tmpTitle;
		}
		
	%>
	<%=printFnName%>("<%=ip%>", "<%=port%>", "<%=format%>", "<%=fixassetCode%>", "<%=rfidNo%>", "<%=fixassetName%>", "<%=fixassetSize%>", "<%=empName%>", "<%=usePositionName%>");
	<%
	}
	%>
}
</script>
		
</body>
</html>