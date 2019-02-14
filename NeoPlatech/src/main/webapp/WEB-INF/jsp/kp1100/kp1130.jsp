<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/doctype.jsp" %>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
String pageTitle = "위치코드관리";
String curAction = "/kp1100/kp1130.do";
String curExcel = "/kp1100/kp1130Excel.do";
CommonMap cmRequest = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
CommonList dataList = RequestUtil.getCommonList(request, "dataList");
%>

<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>

<link type="text/css" rel="stylesheet" href="/common/jstree/_docs/syntax/!style.css"/>
<script type="text/javascript" src="/common/jstree/_lib/jquery.cookie.js"></script>
<script type="text/javascript" src="/common/jstree/_lib/jquery.hotkeys.js"></script>
<script type="text/javascript" src="/common/jstree/jquery.jstree.js"></script>
<script type="text/javascript" src="/common/jstree/_docs/syntax/!script.js"></script>

<script type="text/javascript" src="/common/js/kp1130.js"></script>
<script type="text/javascript">
function fnSearch(){
	var frm = document.sForm;
	frm.target = "_self";
	frm.submit();
}
function fnExcel(){
	if( confirm("<%= pageTitle %>" + "을 엑셀로 저장하시겠습니까?") ){
		var frm = document.sForm;
		frm.action = "<%=curExcel%>";
		frm.submit();
	}
}
$(document).ready(function(){
	fnInitTreeData();
	fnInitTree('Y');
});
$(window).resize(function(){
	fnInitSize();
});
</script>

</head>
<body>

	<%@ include file="/WEB-INF/jsp/common/top.jsp" %>

	<h2 class="title_left"><%= pageTitle %></h2>
	
	<form id="sForm" name="sForm" method="post" action="<%=curAction%>">
	<input type="hidden" id="sPosNo" name="sPosNo" value="" />
	
	<table style="width:100%;border-collapse:collapse;border:0px;">
	<tr>
	<td width="29%" valign="top">
		<div id="treeSearch">
			<table style="width:100%" class="table-search" border="0">
			<colgroup>
				<col width="100px" />
				<col width="" />
			</colgroup>
			<tr>
			<th class="r">코드/명 :</th>
			<td>
				<input type="text" id="searchKeyword" name="searchKeyword" value="<%=cmRequest.getString("searchKeyword")%>" />
				<span class="button"><input type="submit" value="검색" onclick="fnSearch()" ></span>
				<span class="button"><input type="button" value="펼치기" onclick="fnInitTree('Y')"></span>
				<span class="button"><input type="button" value="접기" onclick="fnInitTree('N')"></span>				
			</td>
			</tr>
			</table>
		</div>
		<div id="treeDiv" class="mulpum_tree">
			<div id="tree"></div>
		</div>
	</td>
	<td width="2%">&nbsp;</td>
	<td width="69%" valign="top">
		<div id="contDiv">
		</div>
	</td>
	</tr>
	</table>
	
	<div id="treeData">
		<input type="hidden" name="kp1130TreeData" value="0|위치코드||0|Y" />
		<%
		for(int i=0; i<dataList.size(); i++){
			CommonMap tree = dataList.getMap(i);
			String code = tree.getString("posNo");
			String codeName = "[" + code + "] " + tree.getString("posName");
			String pseq = tree.getString("parentPosNo").trim();
			String useYn = tree.getString("useYn");
			
			if ("0".equals(code)) {
				codeName = tree.getString("posName");
			}
		%>
		<input type="hidden" name="kp1130TreeData" value="<%=code%>|<%=codeName%>|<%=pseq%>|0|<%=useYn%>" />
		<%
		}
		%>
	</div>
	
	</form>
	
	<%@ include file="/WEB-INF/jsp/common/bottom.jsp" %>
	
</body>
</html>